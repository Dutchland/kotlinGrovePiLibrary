package nl.dutchland.grove.rotary

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.grovepiports.AnalogPort
import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.GrovePi

typealias RotaryChangedListener = (Fraction) -> Unit

interface RotarySensor : InputDevice {
    fun getStatus() : Fraction
}