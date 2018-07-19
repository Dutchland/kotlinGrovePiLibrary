package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.grovepiports.AnalogPort
import org.iot.raspberry.grovepi.GrovePi

class GroveLightSensorFactory(private val grovePi : GrovePi) {
    fun createLigthSensorV1_2(pin : AnalogPort) : LightSensor {
        return GroveLightSensor(
                org.iot.raspberry.grovepi.devices.GroveLightSensor(grovePi, pin.analogPin)
        )
    }
}