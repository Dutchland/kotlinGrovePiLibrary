package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveLightSensor

internal class GroveLightSensor(private val sensor : GroveLightSensor) : LightSensor() {
    private val MAX_SENSOR_VALUE = 1023.0

    override fun getStatus(): Fraction {
        val sensorValue = sensor.get().coerceIn(0.0, MAX_SENSOR_VALUE)
        return Fraction.ofFraction(sensorValue / MAX_SENSOR_VALUE)
    }
}
