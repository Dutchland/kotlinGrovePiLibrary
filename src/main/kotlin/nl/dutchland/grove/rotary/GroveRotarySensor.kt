package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveRotarySensor
import org.iot.raspberry.grovepi.devices.GroveRotaryValue
import java.util.*
import kotlin.concurrent.fixedRateTimer

internal class GroveRotarySensor(
        private val sensor: GroveRotarySensor,
        private val listener: RotaryChangedListener) : RotarySensor {
    private var mostRecentStatus: Fraction = currentStatus()
    private var pollRotaryTimer: Timer? = null

    override fun currentStatus(): Fraction {
        val sensorValue = this.sensor.get()
        return sensorValue.toFraction()
    }

    override fun start() {
        this.pollRotaryTimer = fixedRateTimer("Polling rotary task", false, 0, 100)
        { pollRotary() }
    }

    override fun stop() {
        this.pollRotaryTimer?.cancel()
    }

    private fun pollRotary() {
        val newStatus = currentStatus()

        if (this.mostRecentStatus != newStatus) {
            this.mostRecentStatus = newStatus;
            this.listener.invoke(newStatus)
        }
    }
}

private fun GroveRotaryValue.toFraction() : Fraction {
    val turnFraction = this.degrees.coerceIn(0.0, GroveRotarySensor.FULL_ANGLE) / GroveRotarySensor.FULL_ANGLE
    return Fraction.of(turnFraction)
}
