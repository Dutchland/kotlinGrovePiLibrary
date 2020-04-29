package nl.dutchland.grove.button

import nl.dutchland.grove.button.ButtonStatus.*
import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates.observable

class GroveButton internal constructor(
        private val digitalIn: GroveDigitalIn,
        private val listeners: Collection<ButtonStatusChangedListener>) : Button {

    override var status: ButtonStatus
            by observable(pollButton())
            { _, _, newValue ->
                this.listeners.forEach { l -> l.invoke(newValue) }
            }

    private lateinit var pollButtonTimer: Timer

    override fun start() {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100)
        { status = pollButton() }
    }

    override fun stop() {
        this.pollButtonTimer.cancel()
    }

    private fun pollButton(): ButtonStatus {
        return when (this.digitalIn.get()) {
            true -> PRESSED
            else -> NOT_PRESSED
        }
    }
}

