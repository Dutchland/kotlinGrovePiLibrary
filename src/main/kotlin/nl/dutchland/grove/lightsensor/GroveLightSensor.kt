package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.TimeStamp
import org.iot.raspberry.grovepi.GroveAnalogIn
import org.iot.raspberry.grovepi.GroveUtil
import java.util.*
import kotlin.concurrent.fixedRateTimer

private const val MAX_SENSOR_VALUE = 1023.0

internal class GroveLightSensor(private val sensor: GroveAnalogIn) : LightSensor {
    private lateinit var timer: Timer
    private var listeners: List<LightSensorValueListener> = emptyList()
    private var mostRecentValue: LightSensorMeasurement = transform(sensor.get())

    init {
        sensor.setListener { ba -> onSensorChanged(ba) }
    }

    private fun onSensorChanged(ba: ByteArray) {
        mostRecentValue = transform(ba)
        this.listeners.forEach { l -> l.invoke(mostRecentValue) }
    }

    override fun subscribe(listener: LightSensorValueListener) {
        this.listeners += listener
        listener.invoke(mostRecentValue)
    }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 1000)
        { sensor.run() }
    }

    override fun stop() {
        timer.cancel()
    }

    private fun transform(bytes: ByteArray): LightSensorMeasurement {
        val unsignedBytes: IntArray = GroveUtil.unsign(bytes)
        var value = (unsignedBytes[1] * 256).toDouble() + unsignedBytes[2].toDouble()
        value = value.coerceIn(0.0, MAX_SENSOR_VALUE)
        return LightSensorMeasurement(Fraction.of(value / MAX_SENSOR_VALUE), TimeStamp.now())
    }
}
