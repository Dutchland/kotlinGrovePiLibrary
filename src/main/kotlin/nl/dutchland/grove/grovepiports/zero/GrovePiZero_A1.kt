package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A1 internal constructor(): DigitalPort, AnalogPort {
    override fun getAnalogPin(): Int {
        return 1
    }

    override fun getDigitalPin(): Int {
        return 15
    }
}