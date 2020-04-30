package nl.dutchland.grove.ledbutton

import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led

internal class GroveLedButton(private val led: Led, private val button: Button) : LedButton {
    override val status: ButtonStatus = button.status

    override fun stop() {
        button.stop()
        led.stop()
    }

    override fun start() {
        button.start()
    }

    override fun turnOn() {
        led.turnOn()
    }

    override fun turnOff() {
        led.turnOff()
    }
}