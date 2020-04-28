package nl.dutchland.grove.ledbutton

import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led

class GroveLedButton(private val led: Led, button: Button) : LedButton {

    override val status: ButtonStatus = button.status
    override fun turnOn() {
        led.turnOn()
    }

    override fun turnOff() {
        led.turnOff()
    }
}