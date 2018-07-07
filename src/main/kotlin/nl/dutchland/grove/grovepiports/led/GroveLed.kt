package nl.dutchland.grove.grovepiports.led

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveLed

class GroveLed(private val groveLed : GroveLed) : DimmableLed {
    override fun turnOn(brightnessPercentage: FractionalPercentage) {
        val groveBrightness: Int = (brightnessPercentage.fraction * 255).toInt()
        this.groveLed.set(groveBrightness)
    }

    override fun turnOn() {
        this.groveLed.set(true)
    }

    override fun turnOff() {
        this.groveLed.set(false)
    }
}