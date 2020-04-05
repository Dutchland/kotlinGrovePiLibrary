package nl.dutchland.grove.grovepiports

object GrovePi {
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

    object D2 : DigitalPort {
        override val digitalPin = 2
    }

    object D3 : PulseWidthModulationPort {
        override val digitalPin = 3
    }

    object D4 : PulseWidthModulationPort {
        override val digitalPin = 4
    }

    object D5 : PulseWidthModulationPort {
        override val digitalPin = 5
    }

    object D6 : PulseWidthModulationPort {
        override val digitalPin = 6
    }

    object D7 : DigitalPort {
        override val digitalPin = 7
    }

    object D8 : DigitalPort {
        override val digitalPin = 8
    }

    object I2c1 : I2cPort {
        override val i2cDeviceNumber = 1
    }

    object I2c2 : I2cPort {
        override val i2cDeviceNumber = 1
    }

    object I2c3 : I2cPort {
        override val i2cDeviceNumber = 1
    }

    object RPISER
    object SERIAL
}