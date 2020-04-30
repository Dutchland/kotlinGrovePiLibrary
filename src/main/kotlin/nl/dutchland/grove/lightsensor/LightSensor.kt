package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.time.Period

typealias LightSensorValueListener = (LightSensorMeasurement) -> Unit

interface LightSensor : InputDevice {
    val mostRecentValue: LightSensorMeasurement
}