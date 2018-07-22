package nl.dutchland.grove.temperatureandhumidity

interface TemperatureAndHumiditySensor : TemperatureSensor, HumiditySensor {
    fun getTemperatureAndHumidityMeasurement() : TemperatureAndHumidityMeasurement
}