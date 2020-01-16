package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.time.Millisecond
import nl.dutchland.grove.utility.time.Period
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

internal class GroveTemperatureHumiditySensor(private val sensor: GroveTemperatureAndHumiditySensor) : TemperatureHumiditySensor {
    private lateinit var timer: Timer
    private var mostRecentValue: TemperatureHumidityMeasurement
            by Delegates.observable(
                    TemperatureHumidityMeasurement(
                            Temperature.ABSOLUTE_ZERO,
                            RelativeHumidity(Fraction.ZERO),
                            TimeStamp.now()))
            { _, _, newValue ->
                //                this.listeners.forEach { l -> l.onStatusChanged(newValue) }
            }

    override fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 100)
        { mostRecentValue = getTemperatureHumidity() }
    }

    fun stop() {
        this.timer.cancel()
    }

    override fun subscribe(listener: TemperatureHumidityListener, pollInterval: Period) {
        val intervalInMilliseconds: Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue) }

        listener.invoke(mostRecentValue)
    }

    override fun subscribeToTemperature(listener: TemperatureListener, pollInterval: Period) {
        val intervalInMilliseconds: Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue.toTemperatureMeasurement()) }

        listener.invoke(mostRecentValue.toTemperatureMeasurement())
    }

    override fun subscribeToHumidity(listener: HumidityListener, pollInterval: Period) {
        val intervalInMilliseconds: Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue.toHumidityMeasurement()) }

        listener.invoke(mostRecentValue.toHumidityMeasurement())
    }

    override fun getTemperatureHumidity(): TemperatureHumidityMeasurement {
        return pollSensor();
    }

    override fun getHumidity(): HumidityMeasurement {
        return mostRecentValue.toHumidityMeasurement()
    }

    override fun getTemperature(): TemperatureMeasurement {
        return mostRecentValue.toTemperatureMeasurement()
    }

    private fun pollSensor() : TemperatureHumidityMeasurement {
        val sensorValue = this.sensor.get()

        val humidity = RelativeHumidity(Fraction.ofPercentage(sensorValue.humidity))
        val temperature = Temperature.of(sensorValue.temperature, Celcius)

        return TemperatureHumidityMeasurement(temperature, humidity, TimeStamp.now())
    }


}