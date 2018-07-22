package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Temperature

data class TemperatureHumidityMeasurement(
        val temperature: Temperature,
        val humidity: RelativeHumidity,
        val timeStamp : TimeStamp) {
}