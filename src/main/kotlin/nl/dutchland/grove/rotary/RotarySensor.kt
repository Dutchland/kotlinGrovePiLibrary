package nl.dutchland.grove.rotary

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.utility.Fraction

typealias RotaryChangedListener = (Fraction) -> Unit

interface RotarySensor : InputDevice {
    fun addStatusChangedListener(listener: RotaryChangedListener)
    fun getStatus() : Fraction
}