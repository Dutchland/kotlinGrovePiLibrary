package nl.dutchland.grove.co2

import com.pi4j.io.serial.*
import com.pi4j.system.SystemInfo.BoardType
import nl.dutchland.grove.grovepiports.GrovePi
import nl.dutchland.grove.grovepiports.SerialPort
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.time.Millisecond
import nl.dutchland.grove.utility.time.Minute
import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.Second
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timerTask

private val CALIBRATE_COMMAND: ByteArray = byteArrayOf(0xff.toByte(), 0x87.toByte(), 0x87.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0xf2.toByte())
private val REQUEST_MEASUREMENT_COMMAND: ByteArray = byteArrayOf(0xff.toByte(), 0x01.toByte(), 0x86.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x79.toByte())

class GroveCo2Sensor(port: SerialPort, private val listener: Co2Listener) : Co2Sensor {
    private val serial = SerialFactory.createInstance()
    private var timer: Timer? = null

    init {
        val config = SerialConfig()
                .device(port.serialPort)
        serial.open(config)
    }

    override fun start() {
        println("Starting sensor")
        Timer().schedule(timerTask {
            calibrate()
            afterCalibration()
        }, Period.of(0.5, Minute).valueIn(Millisecond).toLong())
    }

    override fun stop() {
        this.timer?.cancel()
    }

    private fun calibrate() {
        println("Calibrating")
        serial.read(9)
        serial.write(CALIBRATE_COMMAND)
    }

    private fun handleEvent(event: SerialDataEvent) {
        println("[HEX DATA]   " + event.hexByteString)
        println("[ASCII DATA] " + event.asciiString)

        listener.invoke(
                GroveCo2Measurement(event.bytes, TimeStamp.now()))
    }

    private fun afterCalibration() {
        Timer().schedule(timerTask {
            println("First read: " + serial.read(9))

            serial.addListener(SerialDataEventListener { event ->
                handleEvent(event)
            })

            initiateTimer()
        }, Period.of(5.0, Second).valueIn(Millisecond).toLong())
    }

    private fun initiateTimer() {
        val pollInterval = Period.of(6.0, Second)
        timer = fixedRateTimer("Polling sensor timer", false, 0, pollInterval.valueIn(Millisecond).toLong()) {
            pollSensor()
        }
    }

    private fun pollSensor() {
        serial.write(REQUEST_MEASUREMENT_COMMAND)
        val measurement = GroveCo2Measurement(serial.read(9), TimeStamp.now())
        listener.invoke(measurement)
    }
}


