package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.PulseWidthModulationPort

class GrovePiZero_D3 internal constructor(): PulseWidthModulationPort {
    override val digitalPin: Int
        get() = 3
}