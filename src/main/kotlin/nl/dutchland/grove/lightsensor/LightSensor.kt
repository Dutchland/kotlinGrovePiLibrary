package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.InputDevice

typealias LightSensorValueListener = (LightSensorMeasurement) -> Unit

interface LightSensor : InputDevice {
    val mostRecentValue: LightSensorMeasurement
}