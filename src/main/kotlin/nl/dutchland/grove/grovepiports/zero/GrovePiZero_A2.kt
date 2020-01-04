package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort

internal class GrovePiZero_A2 internal constructor(): DigitalPort, AnalogPort {
    override val digitalPin = 16
    override val analogPin = 2
}