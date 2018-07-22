package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.time.Millisecond
import nl.dutchland.grove.utility.time.Period
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import kotlin.concurrent.fixedRateTimer

class GroveTemperatureHumiditySensor internal constructor(private val sensor : GroveTemperatureAndHumiditySensor) : TemperatureHumiditySensor{
    private var mostRecentValue = getTemperatureHumidity()

    init {
        fixedRateTimer("Polling sensor timer", false, 100, 100)
        { mostRecentValue = getTemperatureHumidity()}
    }

    override fun subscribeToTemperatureHumidity(listener: TemperatureHumidityListener, pollInterval: Period) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue) }

        listener.invoke(mostRecentValue)
    }

    override fun subscribeToTemperature(listener: TemperatureListener, pollInterval: Period) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue.toTemperatureMeasurement()) }

        listener.invoke(mostRecentValue.toTemperatureMeasurement())
    }

    override fun subscribeToHumidity(listener: HumidityListener, pollInterval: Period) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds)
        { listener.invoke(mostRecentValue.toHumidityMeasurement()) }

        listener.invoke(mostRecentValue.toHumidityMeasurement())
    }

    override fun getTemperatureHumidity(): TemperatureHumidityMeasurement {
        val sensorValue = this.sensor.get()

        val timeStamp = TimeStamp.fromMillisecondsSinceEpoch(System.currentTimeMillis())
        val humidity = RelativeHumidity(FractionalPercentage.ofPercentage(sensorValue.humidity))
        val temperature = Temperature.of(sensorValue.temperature, Celcius)

        return TemperatureHumidityMeasurement(temperature, humidity, timeStamp)
    }

    override fun getHumidity(): HumidityMeasurement {
        return getTemperatureHumidity().toHumidityMeasurement()
    }

    override fun getTemperature(): TemperatureMeasurement {
        return getTemperatureHumidity().toTemperatureMeasurement()
    }

    private fun TemperatureHumidityMeasurement.toHumidityMeasurement() : HumidityMeasurement {
        return HumidityMeasurement(this.humidity, this.timeStamp)
    }

    private fun TemperatureHumidityMeasurement.toTemperatureMeasurement() : TemperatureMeasurement {
                return TemperatureMeasurement(this.temperature, this.timeStamp)
    }
}