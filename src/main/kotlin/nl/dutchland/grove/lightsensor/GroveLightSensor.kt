package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.Interval
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

    override fun subscribe(listener: LightSensorValueListener, pollInterval: Interval) {
        fixedRateTimer("Polling timer", false, pollInterval.inMilliseconds, pollInterval.inMilliseconds) { callListener(listener) }
        callListener(listener)
    }

    private fun callListener(listener: LightSensorValueListener) {
        listener.invoke(this.mostRecentValue)
    }

    override fun getStatus(): FractionalPercentage {
        val sensorValue = getSensorValue()
        return FractionalPercentage.ofFraction(sensorValue / MAX_SENSOR_VALUE)
    }

    private fun getSensorValue(): Double {
        val nonNegativeValue = Math.max(0.0, sensor.get())
        return Math.min(nonNegativeValue, MAX_SENSOR_VALUE)
    }
}
