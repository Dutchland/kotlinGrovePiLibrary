package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.baseunits.temperature.Celsius
import nl.dutchland.grove.utility.baseunits.temperature.Temperature

import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

internal class GroveTemperatureHumiditySensor(
        private val sensor: GroveTemperatureAndHumiditySensor,
        private val listener: TemperatureHumidityListener) : TemperatureHumiditySensor {

    private var timer: Timer? = null
    private var mostRecentValue: TemperatureHumidityMeasurement
            by Delegates.observable(pollSensor())
            { _, _, newValue ->
                this.listener.invoke(newValue)
            }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 6000)
        { mostRecentValue = pollSensor() }
    }

    override fun stop() {
        this.timer?.cancel()
    }

    override fun getTemperatureHumidity(): TemperatureHumidityMeasurement {
        return mostRecentValue
    }

    override fun getHumidity(): HumidityMeasurement {
        return mostRecentValue.toHumidityMeasurement()
    }

    override fun getTemperature(): TemperatureMeasurement {
        return mostRecentValue.toTemperatureMeasurement()
    }

    private fun pollSensor(): TemperatureHumidityMeasurement {
        val sensorValue = this.sensor.get()

        val humidity = RelativeHumidity(Fraction.ofPercentage(sensorValue.humidity))
        val temperature = Temperature.of(sensorValue.temperature, Celsius)

        return TemperatureHumidityMeasurement(temperature, humidity, TimeStamp.now())
    }
}