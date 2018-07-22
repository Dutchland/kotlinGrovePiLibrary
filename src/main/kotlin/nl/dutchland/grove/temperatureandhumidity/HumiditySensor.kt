package nl.dutchland.grove.temperatureandhumidity

typealias HumidityMeasurementListener = (HumidityMeasurement) -> Unit

interface HumiditySensor {
    fun getHumidity() : HumidityMeasurement
}