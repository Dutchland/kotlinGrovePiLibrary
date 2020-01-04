package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.time.Period

typealias LightSensorValueListener = (Fraction) -> Unit

abstract class LightSensor : PollingSensor<Fraction>() {
    override fun subscribe(listener: LightSensorValueListener, pollInterval: Period){
        super.subscribe(listener, pollInterval)
    }
}