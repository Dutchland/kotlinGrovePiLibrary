package nl.dutchland.grove.led

import nl.dutchland.grove.utility.FractionalPercentage

interface DimmableLed : Led {
    fun turnOn(brightnessPercentage: FractionalPercentage)
}