package nl.dutchland.grove.coffeemaker.sensors

import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatusChangedListener

class SimpleButton private constructor(private val listener: ButtonStatusChangedListener) : Button {
    companion object {
        fun withListener(listener: ButtonStatusChangedListener): Button {
            return SimpleButton(listener)
        }
    }

    override val status: ButtonStatus
        get() = TODO("Not yet implemented")

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}