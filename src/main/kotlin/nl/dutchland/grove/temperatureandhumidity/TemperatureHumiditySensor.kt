package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.time.Period

typealias TemperatureHumidityListener = (TemperatureHumidityMeasurement) -> Unit

interface TemperatureHumiditySensor : TemperatureSensor, HumiditySensor {
    fun getTemperatureHumidity() : TemperatureHumidityMeasurement
    fun subscribe(listener: TemperatureHumidityListener)
    fun start()
}