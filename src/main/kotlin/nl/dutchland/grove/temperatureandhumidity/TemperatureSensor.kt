package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.InputDevice

typealias TemperatureListener = (TemperatureMeasurement) -> Unit

interface TemperatureSensor : InputDevice {
    fun getTemperature() : TemperatureMeasurement
}