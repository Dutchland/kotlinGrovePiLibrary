package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A2 internal constructor(): DigitalPort, AnalogPort {
    override fun getAnalogPin(): Int {
        return 2
    }

    override fun getDigitalPin(): Int {
        return 16
    }
}