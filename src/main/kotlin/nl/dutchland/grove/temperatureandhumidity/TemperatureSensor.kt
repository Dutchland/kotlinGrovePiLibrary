package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.utility.time.Period

typealias TemperatureListener = (TemperatureMeasurement) -> Unit

interface TemperatureSensor : InputDevice {
    fun getTemperature() : TemperatureMeasurement
    fun subscribeToTemperature(listener: TemperatureListener)
}