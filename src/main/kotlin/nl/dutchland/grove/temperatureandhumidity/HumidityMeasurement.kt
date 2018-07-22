package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp

class HumidityMeasurement(
        val humidity : RelativeHumidity,
        val timestamp: TimeStamp)