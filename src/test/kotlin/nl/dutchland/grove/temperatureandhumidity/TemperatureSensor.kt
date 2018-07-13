package nl.dutchland.grove.temperatureandhumidity

typealias TemperatureMeasurementListener = (TemperatureMeasurement) -> Unit

interface TemperatureSensor {
    fun getStatus() : TemperatureMeasurement
}