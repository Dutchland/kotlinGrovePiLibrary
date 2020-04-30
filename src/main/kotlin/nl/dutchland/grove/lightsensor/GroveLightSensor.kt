package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.TimeStamp
import org.iot.raspberry.grovepi.GroveAnalogIn
import org.iot.raspberry.grovepi.GroveUtil
import java.util.*
import kotlin.concurrent.fixedRateTimer

private const val MAX_SENSOR_VALUE = 1023.0

internal class GroveLightSensor(private val sensor: GroveAnalogIn,
                                private val listener: LightSensorValueListener) : LightSensor {
    override var mostRecentValue: LightSensorMeasurement = sensor.get().toLightMeasurement()
    private var timer: Timer? = null

    init {
        sensor.setListener { ba -> onSensorChanged(ba) }
    }

    private fun onSensorChanged(byteArray: ByteArray) {
        mostRecentValue = byteArray.toLightMeasurement()
        this.listener.invoke(mostRecentValue)
    }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 1000)
        { sensor.run() }
    }

    override fun stop() {
        timer?.cancel()
    }

    private fun ByteArray.toLightMeasurement() : LightSensorMeasurement {
        val unsignedBytes: IntArray = GroveUtil.unsign(this)
        var value = (unsignedBytes[1] * 256).toDouble() + unsignedBytes[2].toDouble()
        value = value.coerceIn(0.0, MAX_SENSOR_VALUE)
        return LightSensorMeasurement(Fraction.of(value / MAX_SENSOR_VALUE), TimeStamp.now())
    }
}
