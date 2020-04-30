package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveLed
import kotlin.math.ceil

private const val MAX_PULSE_WIDTH_VALUE = 255

internal abstract class GrovePulseWidthModulationOutputDevice(private val groveLed : GroveLed) : PulseWidthModulationOutputDevice {

    override fun turnOn(percentage: Fraction) {
        val grovePiNumber = ceil(percentage.fraction * MAX_PULSE_WIDTH_VALUE).toInt()
        this.groveLed.set(grovePiNumber)
    }

    override fun turnOn() {
        this.groveLed.set(true)
    }

    override fun turnOff() {
        this.groveLed.set(false)
    }
}
