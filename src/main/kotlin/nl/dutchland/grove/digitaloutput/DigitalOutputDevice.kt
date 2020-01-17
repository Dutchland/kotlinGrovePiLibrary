package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.OutputDevice

interface DigitalOutputDevice : OutputDevice {
    fun turnOn()
    fun turnOff()

    override fun stop() {
        turnOff()
    }
}