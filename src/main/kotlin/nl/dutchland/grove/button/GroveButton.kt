package nl.dutchland.grove.button

import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GroveButton internal constructor(private val digitalIn : GroveDigitalIn) : Button {
    private var statusChangedListener : Collection<(Boolean) -> Unit> = ArrayList()
    private val fixedRateTimer: Timer

    private var currentStatus : Boolean = false;

    init {
        this.fixedRateTimer = fixedRateTimer("Polling button task", false, 0, 100) { pollButton()}
    }

    private fun pollButton() {
        val newStatus : Boolean = this.digitalIn.get()

        if (this.currentStatus != newStatus) {
            this.currentStatus = newStatus;
            this.onStatusChanged(newStatus)
        }

        println("Polling button")
    }

    override fun addStatusChangedListener(listener: (Boolean) -> Unit) {
        val newListeners = ArrayList(this.statusChangedListener)
        newListeners.add(listener)

        this.statusChangedListener = newListeners
    }

    override fun isPressed(): Boolean {
        pollButton()
        return this.currentStatus
    }

    private fun onStatusChanged(isPressed : Boolean) {
        this.statusChangedListener.parallelStream()
                .forEach{ l -> l.invoke(isPressed) }
    }
}
