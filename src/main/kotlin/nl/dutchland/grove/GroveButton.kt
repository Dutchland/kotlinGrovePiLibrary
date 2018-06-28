package nl.dutchland.grove

import nl.dutchland.grove.button.Button
import org.iot.raspberry.grovepi.GroveDigitalIn

class GroveButton internal constructor(private val digitalIn : GroveDigitalIn) : Button {
    override fun isPressed(): Boolean {
        return digitalIn.get()
    }
}
