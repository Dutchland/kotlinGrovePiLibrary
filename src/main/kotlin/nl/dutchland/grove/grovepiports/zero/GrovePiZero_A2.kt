package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A2 internal constructor(): DigitalPort, AnalogPort {
    override val digitalPin: Int
        get() = 16
    override val analogPin: Int
        get() = 2
}