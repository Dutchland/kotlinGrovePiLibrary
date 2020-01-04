package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveRotarySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer

internal class GroveRotarySensor(private val sensor: GroveRotarySensor) : RotarySensor {
    private var mostRecentStatus: Fraction = getStatus()
    private var statusChangedListeners : Collection<RotaryChangedListener> = ArrayList()
    private var pollRotaryTimer: Timer

    init {
        this.pollRotaryTimer = fixedRateTimer("Polling rotary task", false, 0, 100) { pollRotary()}
    }

    override fun addStatusChangedListener(listener: RotaryChangedListener) {
        val newListeners = ArrayList(this.statusChangedListeners)
        newListeners.add(listener)

        this.statusChangedListeners = newListeners

        listener.invoke(getStatus())
    }

    private fun pollRotary() {
        val newStatus = getStatus()

        if (this.mostRecentStatus != newStatus) {
            this.mostRecentStatus = newStatus;
            this.onStatusChanged(newStatus)
        }
    }

    override fun getStatus(): Fraction {
        val sensorValue = this.sensor.get()
        val turnFraction =  sensorValue.degrees.coerceIn(0.0, GroveRotarySensor.FULL_ANGLE) / GroveRotarySensor.FULL_ANGLE
        return Fraction.ofFraction(turnFraction)
    }

    private fun onStatusChanged(newStatus : Fraction) {
        this.statusChangedListeners.parallelStream()
                .forEach{ l -> l.invoke(newStatus) }
    }
}