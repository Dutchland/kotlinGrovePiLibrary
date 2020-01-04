package nl.dutchland.grove.grovepiports.zero

import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.grovepiports.PulseWidthModulationPort

object GrovePiZero {
    object A0 : DigitalPort, AnalogPort {
        override val digitalPin = 14
        override val analogPin = 0
    }

    object A1 : DigitalPort, AnalogPort {
        override val digitalPin = 15
        override val analogPin = 1
    }

    object A2 : DigitalPort, AnalogPort {
        override val digitalPin = 16
        override val analogPin = 2
    }

    object D3 : PulseWidthModulationPort {
        override val digitalPin = 3
    }

    object RPISER
    object I2c
}