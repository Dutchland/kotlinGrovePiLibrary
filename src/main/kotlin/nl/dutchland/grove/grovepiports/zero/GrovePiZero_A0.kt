package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

class GrovePiZero_A0 internal constructor(): DigitalPort, AnalogPort {
    override val digitalPin = 14
    override val analogPin = 0
}