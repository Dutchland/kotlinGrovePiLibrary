package nl.dutchland.grove.button

import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GroveButton internal constructor(private val digitalIn : GroveDigitalIn) : Button {
    private var statusChangedListeners : Collection<ButtonStatusChangedListener> = ArrayList()
    private val pollButtonTimer: Timer

    private var currentStatus : Boolean = false;

    init {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100) { pollButton()}
    }

    private fun pollButton() {
        val newStatus : Boolean = this.digitalIn.get()

        if (this.currentStatus != newStatus) {
            this.currentStatus = newStatus;
            this.onStatusChanged(newStatus)
        }

        println("Polling button")
    }

    override fun addStatusChangedListener(listener: ButtonStatusChangedListener) {
        val newListeners = ArrayList(this.statusChangedListeners)
        newListeners.add(listener)

        this.statusChangedListeners = newListeners
    }

    override fun isPressed(): Boolean {
        pollButton()
        return this.currentStatus
    }

    private fun onStatusChanged(isPressed : Boolean) {
        this.statusChangedListeners.parallelStream()
                .forEach{ l -> l.invoke(isPressed) }
    }
}
