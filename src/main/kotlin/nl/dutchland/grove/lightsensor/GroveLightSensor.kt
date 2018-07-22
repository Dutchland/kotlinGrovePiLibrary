package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.Millisecond
import org.iot.raspberry.grovepi.devices.GroveLightSensor
import kotlin.concurrent.fixedRateTimer

internal class GroveLightSensor(private val sensor : GroveLightSensor) : LightSensor {
    private val MAX_SENSOR_VALUE = 1023.0

    private var mostRecentValue: FractionalPercentage = getStatus()

    init {
        fixedRateTimer("Polling timer", false, 100, 100)
        { mostRecentValue = getStatus()}
    }

    override fun subscribe(listener: LightSensorValueListener, pollInterval: Period) {
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
