package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A0 internal constructor(): DigitalPort, AnalogPort {
    override fun getAnalogPin(): Int {
        return 0
    }

    override fun getDigitalPin(): Int {
        return 14
    }
}