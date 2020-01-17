package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.InputDevice

typealias TemperatureHumidityListener = (TemperatureHumidityMeasurement) -> Unit

interface TemperatureHumiditySensor : TemperatureSensor, HumiditySensor, InputDevice {
    fun getTemperatureHumidity() : TemperatureHumidityMeasurement
    fun subscribe(listener: TemperatureHumidityListener)
}