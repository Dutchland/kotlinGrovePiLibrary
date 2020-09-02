package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.InputDevice

typealias HumidityListener = (HumidityMeasurement) -> Unit

interface HumiditySensor: InputDevice {
    fun getHumidity() : HumidityMeasurement
}