package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.baseunits.temperature.Temperature

data class TemperatureHumidityMeasurement(
        val temperature: Temperature,
        val humidity: RelativeHumidity,
        val timeStamp : TimeStamp) {

    fun toHumidityMeasurement() : HumidityMeasurement {
        return HumidityMeasurement(this.humidity, this.timeStamp)
    }

    fun toTemperatureMeasurement() : TemperatureMeasurement {
        return TemperatureMeasurement(this.temperature, this.timeStamp)
    }
}