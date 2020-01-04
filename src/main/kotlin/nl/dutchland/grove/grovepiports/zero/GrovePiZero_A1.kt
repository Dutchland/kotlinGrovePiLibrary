package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

internal class GrovePiZero_A1 internal constructor(): DigitalPort, AnalogPort {
    override val digitalPin = 15
    override val analogPin = 1
}