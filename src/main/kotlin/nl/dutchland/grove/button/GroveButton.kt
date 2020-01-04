package nl.dutchland.grove.button

import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GroveButton internal constructor(private val digitalIn: GroveDigitalIn, private val listeners: Collection<ButtonStatusChangedListener>) : Button {
    override var status: ButtonStatus = ButtonStatus.NOT_PRESSED
        private set

    fun start() {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100) { pollButton() }
    }

    fun stop() {
        this.pollButtonTimer.cancel()
    }

    private lateinit var pollButtonTimer: Timer

    private fun pollButton() {
        val newStatus: ButtonStatus = getStatusFromDigitalIn()

        if (this.status != newStatus) {
            this.status = newStatus
            this.onStatusChanged(newStatus)
        }
    }

    private fun getStatusFromDigitalIn(): ButtonStatus {
        return when (this.digitalIn.get()) {
            true -> ButtonStatus.PRESSED
            else -> {
                ButtonStatus.NOT_PRESSED
            }
        }
    }

    private fun onStatusChanged(newStatus: ButtonStatus) {
        this.listeners.parallelStream()
                .forEach { l -> l.invoke(newStatus) }
    }
}