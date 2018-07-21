package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

class GroveTemperatureAndHumiditySensor internal constructor(private val sensor : GroveTemperatureAndHumiditySensor) : TemperatureSensor{
    override fun getStatus(): TemperatureMeasurement {
        val sensorValue = this.sensor.get()

        val temperature = Temperature.of(sensorValue.temperature, Celcius)
        val timeStamp = TimeStamp.fromMillisecondsSinceEpoch(System.currentTimeMillis())
        return TemperatureMeasurement(temperature, timeStamp)
    }
}