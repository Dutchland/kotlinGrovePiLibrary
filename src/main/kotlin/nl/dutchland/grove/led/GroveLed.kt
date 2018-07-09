package nl.dutchland.grove.led

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveLed

class GroveLed(private val groveLed : GroveLed) : DimmableLed {
    override fun turnOn(brightnessPercentage: FractionalPercentage) {
        val groveBrightnessNumber = Math.ceil(brightnessPercentage.fraction * GroveLed.MAX_BRIGTHNESS).toInt()
        this.groveLed.set(groveBrightnessNumber)
    }

    override fun turnOn() {
        this.groveLed.set(true)
    }

    override fun turnOff() {
        this.groveLed.set(false)
    }
}
