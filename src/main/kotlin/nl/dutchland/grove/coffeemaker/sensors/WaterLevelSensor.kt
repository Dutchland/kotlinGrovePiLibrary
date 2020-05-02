package nl.dutchland.grove.coffeemaker.sensors

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.utility.Fraction

class WaterLevelSensor private constructor(private val listener: (Fraction) -> Unit) : InputDevice {
    companion object {
        fun withListener(listener: (Fraction) -> Unit): WaterLevelSensor {
            return WaterLevelSensor(listener)
        }
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}