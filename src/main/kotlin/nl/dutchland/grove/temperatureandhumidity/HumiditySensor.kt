package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.utility.time.Period

typealias HumidityListener = (HumidityMeasurement) -> Unit

interface HumiditySensor: InputDevice {
    fun getHumidity() : HumidityMeasurement
    fun subscribeToHumidity(listener: HumidityListener)
}