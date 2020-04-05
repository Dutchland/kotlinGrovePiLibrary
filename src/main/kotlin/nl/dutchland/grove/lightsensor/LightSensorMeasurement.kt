package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.TimeStamp

data class LightSensorMeasurement(val value: Fraction, val timestamp: TimeStamp)