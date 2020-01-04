package nl.dutchland.grove.button

import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates.observable

class GroveButton internal constructor(
        private val digitalIn: GroveDigitalIn,
        private val listeners: Collection<ButtonStatusChangedListener>) : Button {

    override var status: ButtonStatus
            by observable(ButtonStatus.NOT_PRESSED)
            { _, _, newValue ->
                this.listeners.forEach { l -> l.invoke(newValue) }
            }

    private lateinit var pollButtonTimer: Timer


    fun start() {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100)
        { pollButton() }
    }

    fun stop() {
        this.pollButtonTimer.cancel()
    }


    private fun pollButton() {
        this.status = getStatusFromDigitalIn()
    }

    private fun getStatusFromDigitalIn(): ButtonStatus {
        return when (this.digitalIn.get()) {
            true -> ButtonStatus.PRESSED
            else -> ButtonStatus.NOT_PRESSED
        }
    }
}

