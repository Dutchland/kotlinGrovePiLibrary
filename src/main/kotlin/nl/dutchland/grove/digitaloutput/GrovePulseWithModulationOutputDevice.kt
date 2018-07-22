package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveLed

internal abstract class GrovePulseWithModulationOutputDevice(private val groveLed : GroveLed) : PulseWidthModulationOutputDevice {
    override fun turnOn(percentage: FractionalPercentage) {
        val grovePiNumber = Math.ceil(percentage.fraction * GroveLed.MAX_BRIGTHNESS).toInt()
        this.groveLed.set(grovePiNumber)
    }

    override fun turnOn() {
        this.groveLed.set(true)
    }

    override fun turnOff() {
        this.groveLed.set(false)
    }
}
