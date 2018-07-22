package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.time.Period

typealias TemperatureListener = (TemperatureMeasurement) -> Unit

interface TemperatureSensor {
    fun getTemperature() : TemperatureMeasurement
    fun subscribeToTemperature(listener: TemperatureListener, pollInterval: Period)
}