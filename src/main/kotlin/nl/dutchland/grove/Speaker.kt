package nl.dutchland.grove

import nl.dutchland.grove.buzzer.AdjustableBuzzer
import nl.dutchland.grove.utility.Fraction

class Speaker(private val buzzer: AdjustableBuzzer) {
    private var volume: Fraction = Fraction.ZERO
    var isMuted = false

    fun mute() {
        buzzer.turnOff()
        isMuted = true
    }

    fun unMute() {
        buzzer.turnOn(volume)
        isMuted = false
    }

    fun setVolume(percentage: Fraction) {
        this.volume = percentage

        if (!isMuted) {
            buzzer.turnOn(percentage)
        }
    }
}