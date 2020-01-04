package nl.dutchland.grove.button

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.iot.raspberry.grovepi.GrovePi

class GroveButtonFactory(private val grovePi: GrovePi) {
    fun aButton(port: DigitalPort, listeners: Collection<ButtonStatusChangedListener>): GroveButton {
        return GroveButton(
                GroveDigitalIn(this.grovePi, port.digitalPin), listeners)
    }
}