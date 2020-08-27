package nl.dutchland.grove.co2

import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature

internal class GroveCo2Measurement(
        data: ByteArray,
        override val timestamp: TimeStamp) : Co2Measurement {

    override val concentration : AirConcentration
    override val temperature : Temperature

    init {
        concentration = GroveCo2AirConcentration(data)
        temperature = Temperature.of((data[4] - 40).toDouble(), Celsius)
    }
}