package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.time.Duration

typealias LightSensorValueListener = (FractionalPercentage) -> Unit

interface LightSensor {
    fun getStatus() : FractionalPercentage
    fun subscribe(listener: LightSensorValueListener, pollInterval: Duration)
}