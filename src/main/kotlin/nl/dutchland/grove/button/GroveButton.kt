package nl.dutchland.grove.button

import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GroveButton internal constructor(private val digitalIn : GroveDigitalIn) : Button {
    private var statusChangedListeners : Collection<ButtonStatusChangedListener> = listOf()
    private val pollButtonTimer: Timer

    private var currentStatus : Boolean = getStatus();

    init {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100) { pollButton()}
    }

    private fun pollButton() {
        val newStatus : Boolean = getStatus()

        if (this.currentStatus != newStatus) {
            this.currentStatus = newStatus;
            this.onStatusChanged(newStatus)
        }
    }

    override fun addStatusChangedListener(listener: ButtonStatusChangedListener) {
        val newListeners = ArrayList(this.statusChangedListeners)
        newListeners.add(listener)
        this.statusChangedListeners = newListeners

        listener.invoke(getStatus())
    }

    private fun getStatus() : Boolean {
        return this.digitalIn.get();
    }

    override fun isPressed(): Boolean {
        return getStatus()
    }

    private fun onStatusChanged(isPressed : Boolean) {
        this.statusChangedListeners.parallelStream()
                .forEach{ l -> l.invoke(isPressed) }
    }
}
