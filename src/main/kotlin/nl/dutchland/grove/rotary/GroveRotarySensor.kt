package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveRotarySensor
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GroveRotarySensor internal constructor(private val sensor: GroveRotarySensor) : RotarySensor {
    private var mostRecentStatus: FractionalPercentage = getStatus()
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

    override fun getStatus(): FractionalPercentage {
        return FractionalPercentage.ofFraction(getSensorTurnFraction())
    }

    private fun getSensorTurnFraction() : Double {
        val sensorValue = this.sensor.get()

        val angle = Math.min(GroveRotarySensor.FULL_ANGLE, sensorValue.degrees)
        return Math.max(0.0, angle) / GroveRotarySensor.FULL_ANGLE
    }

    private fun onStatusChanged(newStatus : FractionalPercentage) {
        this.statusChangedListeners.parallelStream()
                .forEach{ l -> l.invoke(newStatus) }
    }
}