package nl.dutchland.grove.co2

import com.pi4j.io.serial.*
import com.pi4j.system.SystemInfo.BoardType
import nl.dutchland.grove.InputDevice
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

typealias Co2Listener = (Co2Measurement) -> Unit

private val CALIBRATE_COMMAND: ByteArray = byteArrayOf(0xff.toByte(), 0x87.toByte(), 0x87.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0xf2.toByte())
private val REQUEST_MEASUREMENT_COMMAND: ByteArray = byteArrayOf(0xff.toByte(), 0x01.toByte(), 0x86.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x79.toByte())

class GroveCo2Sensor(boardType: BoardType, private val listener: Co2Listener) : InputDevice {
    private val serial = SerialFactory.createInstance()
    private var timer: Timer? = null

    private val config = SerialConfig()
            .device(SerialPort.getDefaultPort(boardType))
            .baud(Baud._9600)
            .dataBits(DataBits._8)
            .parity(Parity.NONE)
            .stopBits(StopBits._1)
            .flowControl(FlowControl.NONE)

    init {
        serial.open(config)
    }

    override fun start() {
        Timer().schedule(timerTask {
            serial.write(REQUEST_MEASUREMENT_COMMAND)
            serial.write(CALIBRATE_COMMAND)
            afterCalibration()
        }, Period.of(3.0, Minute).valueIn(Millisecond).toLong())
    }

    override fun stop() {
        this.timer?.cancel()
    }

    private fun handleEvent(event: SerialDataEvent) {
        println("[HEX DATA]   " + event.hexByteString)
        println("[ASCII DATA] " + event.asciiString)

        val bytes = event.bytes
        val highLevel = bytes[2]
        val lowLevel = bytes[3]
        val temperatureInCelcius = (bytes[4] - 40).toDouble()
        val ppm = highLevel * 265 + lowLevel

        listener.invoke(Co2Measurement(
                ppm,
                Temperature.of(temperatureInCelcius, Celsius),
                TimeStamp.now()))
    }

    private fun afterCalibration() {
        Timer().schedule(timerTask {
            serial.addListener(SerialDataEventListener { event ->
                handleEvent(event)
            })

            timer = fixedRateTimer("Polling sensor timer", false, 0, 6000)
            { serial.write(REQUEST_MEASUREMENT_COMMAND) }
        }, Period.of(5.0, Second).valueIn(Millisecond).toLong())
    }
}