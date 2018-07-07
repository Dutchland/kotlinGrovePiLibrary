package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A1 internal constructor(): DigitalPort, AnalogPort {
    override val digitalPin: Int
        get() = 15
    override val analogPin: Int
        get() = 1
}