package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.Temperature
import nl.dutchland.grove.utility.TimeStamp

data class TemperatureMeasurement(val temperature : Temperature, val timestamp: TimeStamp)
