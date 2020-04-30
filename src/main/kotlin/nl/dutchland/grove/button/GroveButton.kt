package nl.dutchland.grove.button

import nl.dutchland.grove.button.ButtonStatus.*
import org.iot.raspberry.grovepi.GroveDigitalIn
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates.observable

internal class GroveButton internal constructor(
        private val digitalIn: GroveDigitalIn,
        private val listener: ButtonStatusChangedListener) : Button {

    override var status: ButtonStatus
            by observable(pollButton())
            { _, _, newValue ->
                listener.invoke(newValue)
            }

    private var pollButtonTimer: Timer? = null

    override fun start() {
        this.pollButtonTimer = fixedRateTimer("Polling button task", false, 0, 100)
        { status = pollButton() }
    }

    override fun stop() {
        this.pollButtonTimer?.cancel()
    }

    private fun pollButton(): ButtonStatus {
        return when (this.digitalIn.get()) {
            true -> PRESSED
            else -> NOT_PRESSED
        }
    }
}

