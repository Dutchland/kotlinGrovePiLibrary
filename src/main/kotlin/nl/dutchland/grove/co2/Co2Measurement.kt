package nl.dutchland.grove.co2

import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Temperature

class Co2Measurement(
        val ppm: Int,
        val temperature: Temperature,
        val timestamp: TimeStamp
)