package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.time.Period

typealias LightSensorValueListener = (FractionalPercentage) -> Unit

abstract class LightSensor : PollingSensor<FractionalPercentage>() {
    override fun subscribe(listener: LightSensorValueListener, pollInterval: Period){
        super.subscribe(listener, pollInterval)
    }
}