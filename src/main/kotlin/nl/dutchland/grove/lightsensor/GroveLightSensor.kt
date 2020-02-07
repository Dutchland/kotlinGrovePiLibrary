package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.time.Period
import org.iot.raspberry.grovepi.devices.GroveLightSensor
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

private const val MAX_SENSOR_VALUE = 1023.0

class GroveLightSensor(private val sensor: GroveLightSensor) : LightSensor {
    private lateinit var timer: Timer
    private var listeners: List<LightSensorValueListener> = emptyList()
    private var mostRecentValue: Fraction
            by Delegates.observable(poll())
            { _, _, newValue ->
                this.listeners.forEach { l -> l.invoke(newValue) }
            }

    override fun subscribe(listener: LightSensorValueListener) {
        this.listeners += listener
    }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 1000)
        { mostRecentValue = poll() }
    }

    override fun stop() {
        this.timer.cancel()
    }

    fun poll(): Fraction {
        val sensorValue = sensor.get().coerceIn(0.0, MAX_SENSOR_VALUE)
        return Fraction.ofFraction(sensorValue / MAX_SENSOR_VALUE)
    }
}
