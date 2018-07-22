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

    }

    override fun subscribeToTemperature(listener: TemperatureListener, pollInterval: Period) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Calling listener", false, intervalInMilliseconds, intervalInMilliseconds) { callListener(listener) }
        callListener(listener)
    }

    private fun callListener(listener: TemperatureListener) {
        listener.invoke(TemperatureMeasurement(
                this.mostRecentValue.temperature,
                this.mostRecentValue.timeStamp))
    }

    override fun subscribeToHumidity(listener: HumidityListener, pollInterval: Period) {

    }

    override fun getTemperatureHumidity(): TemperatureHumidityMeasurement {
        val sensorValue = this.sensor.get()

        val humidity = RelativeHumidity(FractionalPercentage.ofPercentage(sensorValue.humidity))
        val temperature = Temperature.of(sensorValue.temperature, Celcius)
        val timeStamp = TimeStamp.fromMillisecondsSinceEpoch(System.currentTimeMillis())
        return TemperatureHumidityMeasurement(temperature, humidity, timeStamp)
    }

    override fun getHumidity(): HumidityMeasurement {
        val temperatureAndHumidity = getTemperatureHumidity()
        return HumidityMeasurement(temperatureAndHumidity.humidity, temperatureAndHumidity.timeStamp)
    }

    override fun getTemperature(): TemperatureMeasurement {
        val temperatureAndHumidity = getTemperatureHumidity()
        return TemperatureMeasurement(temperatureAndHumidity.temperature, temperatureAndHumidity.timeStamp)
    }
}