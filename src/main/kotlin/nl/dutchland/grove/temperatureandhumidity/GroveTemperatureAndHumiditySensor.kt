package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

class GroveTemperatureAndHumiditySensor internal constructor(private val sensor : GroveTemperatureAndHumiditySensor) : TemperatureSensor, HumiditySensor{
    override fun getHumidity(): HumidityMeasurement {
        val sensorValue = this.sensor.get()

        val humidity = RelativeHumidity(FractionalPercentage.ofPercentage(sensorValue.humidity))
        val timeStamp = TimeStamp.fromMillisecondsSinceEpoch(System.currentTimeMillis())
        return HumidityMeasurement(humidity, timeStamp)
    }

    override fun getTemperature(): TemperatureMeasurement {
        val sensorValue = this.sensor.get()

        val temperature = Temperature.of(sensorValue.temperature, Celcius)
        val timeStamp = TimeStamp.fromMillisecondsSinceEpoch(System.currentTimeMillis())
        return TemperatureMeasurement(temperature, timeStamp)
    }
}