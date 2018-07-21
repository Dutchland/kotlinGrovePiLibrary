package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.time.Duration
import nl.dutchland.grove.utility.time.Millisecond
import org.iot.raspberry.grovepi.devices.GroveLightSensor
import kotlin.concurrent.fixedRateTimer

class GroveLightSensor internal constructor(private val sensor : GroveLightSensor) : LightSensor {
    private val MAX_SENSOR_VALUE = 1023.0

    private var mostRecentValue: FractionalPercentage = getStatus()

    init {
        fixedRateTimer("Polling timer", false, 0, 100) { pollSensor()}
    }

    private fun pollSensor() {
        this.mostRecentValue = getStatus()
    }

    override fun subscribe(listener: LightSensorValueListener, pollInterval: Duration) {
        val intervalInMilliseconds : Long = pollInterval.valueIn(Millisecond).toLong()
        fixedRateTimer("Polling timer", false, intervalInMilliseconds, intervalInMilliseconds) { callListener(listener) }
        callListener(listener)
    }

    private fun callListener(listener: LightSensorValueListener) {
        listener.invoke(this.mostRecentValue)
    }

    override fun getStatus(): FractionalPercentage {
        val sensorValue = sensor.get().coerceIn(0.0, MAX_SENSOR_VALUE)
        return FractionalPercentage.ofFraction(sensorValue / MAX_SENSOR_VALUE)
    }

}
