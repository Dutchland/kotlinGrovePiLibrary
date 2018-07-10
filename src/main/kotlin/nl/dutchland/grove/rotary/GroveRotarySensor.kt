package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveRotarySensor

class GroveRotarySensor internal constructor(private val sensor: GroveRotarySensor) : RotarySensor {
    override fun addStatusChangedListener(listener: RotaryChangedListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStatus(): FractionalPercentage {
        return FractionalPercentage.ofFraction(getSensorTurnFraction())
    }

    private fun getSensorTurnFraction() : Double {
        val sensorValue = this.sensor.get()

        val angle = Math.min(GroveRotarySensor.FULL_ANGLE, sensorValue.degrees)
        return Math.max(0.0, angle) / GroveRotarySensor.FULL_ANGLE
    }
}