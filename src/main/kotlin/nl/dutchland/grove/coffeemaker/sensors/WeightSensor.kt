package nl.dutchland.grove.coffeemaker.sensors

import nl.dutchland.grove.InputDevice

class WeightSensor private constructor(private val listener: (Double) -> Unit) : InputDevice {
    companion object {
        fun withListener(listener: (Double) -> Unit): WeightSensor {
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