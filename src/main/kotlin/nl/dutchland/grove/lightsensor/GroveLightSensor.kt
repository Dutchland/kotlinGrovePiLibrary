package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveLightSensor

class GroveLightSensor internal constructor(private val sensor : GroveLightSensor) : LightSensor {
    private val MAX_SENSOR_VALUE = 1023.0

    override fun getStatus(): FractionalPercentage {
        val sensorValue = getSensorValue()
        return FractionalPercentage.ofFraction(sensorValue / MAX_SENSOR_VALUE)
    }

    private fun getSensorValue(): Double {
        val nonNegativeValue = Math.max(0.0, sensor.get())
        return Math.min(nonNegativeValue, MAX_SENSOR_VALUE)
    }
}
