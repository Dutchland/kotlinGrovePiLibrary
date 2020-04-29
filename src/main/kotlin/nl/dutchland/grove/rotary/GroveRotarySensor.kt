package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveRotarySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer

internal class GroveRotarySensor(private val sensor: GroveRotarySensor, listeners: Array<out RotaryChangedListener>) : RotarySensor {
    private var mostRecentStatus: Fraction = getStatus()
    private val statusChangedListeners: Collection<RotaryChangedListener> = listeners.asList()
    private lateinit var pollRotaryTimer: Timer

    private fun pollRotary() {
        val newStatus = getStatus()

        if (this.mostRecentStatus != newStatus) {
            this.mostRecentStatus = newStatus;
            this.onStatusChanged(newStatus)
        }
    }

    override fun getStatus(): Fraction {
        val sensorValue = this.sensor.get()
        val turnFraction = sensorValue.degrees.coerceIn(0.0, GroveRotarySensor.FULL_ANGLE) / GroveRotarySensor.FULL_ANGLE
        return Fraction.of(turnFraction)
    }

    override fun start() {
        this.pollRotaryTimer = fixedRateTimer("Polling rotary task", false, 0, 100) { pollRotary() }
    }

    override fun stop() {
        this.pollRotaryTimer.cancel()
    }

    private fun onStatusChanged(newStatus: Fraction) {
        this.statusChangedListeners.parallelStream()
                .forEach { l -> l.invoke(newStatus) }
    }
}