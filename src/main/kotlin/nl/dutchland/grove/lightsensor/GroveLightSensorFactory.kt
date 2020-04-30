package nl.dutchland.grove.lightsensor

import nl.dutchland.grove.grovepiports.AnalogPort
import org.iot.raspberry.grovepi.GrovePi

class GroveLightSensorFactory(private val grovePi: GrovePi) {
    fun createLigthSensorV1_2(pin: AnalogPort, listener: LightSensorValueListener): LightSensor {
        return GroveLightSensor(grovePi.getAnalogIn(pin.analogPin, 4), listener)
    }
}