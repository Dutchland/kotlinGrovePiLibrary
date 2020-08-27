package nl.dutchland.grove.coffeemaker.sensors

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.utility.mass.Mass

class WeightSensor private constructor(private val listener: (Mass) -> Unit) : InputDevice {
    companion object {
        fun withListener(listener: (Mass) -> Unit): WeightSensor {
            return WeightSensor(listener)
        }
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}