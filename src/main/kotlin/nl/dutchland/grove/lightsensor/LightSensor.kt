package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage

interface LightSensor {
    fun getStatus() : FractionalPercentage
}