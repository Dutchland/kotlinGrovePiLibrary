package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.FractionalPercentage

typealias RotaryChangedListener = (FractionalPercentage) -> Unit

interface RotarySensor {
    fun addStatusChangedListener(listener: RotaryChangedListener)
    fun getStatus() : FractionalPercentage
}