package nl.dutchland.grove.co2

import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Temperature

interface Co2Measurement {
    val concentration: AirConcentration
    val temperature: Temperature
    val timestamp: TimeStamp
}