package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature

import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

internal class GroveTemperatureHumiditySensor(private val sensor: GroveTemperatureAndHumiditySensor) : TemperatureHumiditySensor {
    private var timer: Timer? = null
    private var listeners: Collection<TemperatureHumidityListener> = emptyList()
    private var mostRecentValue: TemperatureHumidityMeasurement
            by Delegates.observable(pollSensor())
            { _, _, newValue ->
                this.listeners.forEach { l -> l.invoke(newValue) }
            }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 6000)
        { mostRecentValue = pollSensor() }
    }

    override fun stop() {
        this.timer?.cancel()
    }

    override fun subscribe(listener: TemperatureHumidityListener) {
        this.listeners += listener
    }

    override fun subscribeToTemperature(listener: TemperatureListener) {
        this.listeners += { th -> listener.invoke(th.toTemperatureMeasurement()) }
    }

    override fun subscribeToHumidity(listener: HumidityListener) {
        this.listeners += { th -> listener.invoke(th.toHumidityMeasurement()) }
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