package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.time.Period

typealias HumidityListener = (HumidityMeasurement) -> Unit

interface HumiditySensor {
    fun getHumidity() : HumidityMeasurement
    fun subscribeToHumidity(listener: HumidityListener, pollInterval: Period)
}