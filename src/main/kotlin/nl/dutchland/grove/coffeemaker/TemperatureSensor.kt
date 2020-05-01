package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.temperatureandhumidity.TemperatureListener

class TemperatureSensor private constructor(private val listener: TemperatureListener) : InputDevice {
    companion object {
        fun withListener(listener: TemperatureListener): TemperatureSensor {
            return TemperatureSensor(listener)
        }
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}