package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.grovepiports.AnalogPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

class GroveTemperatureHumiditySensorFactory(private val grovePi : GrovePi) {
    fun createDHT11(port: AnalogPort) : TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.analogPin, GroveTemperatureAndHumiditySensor.Type.DHT11)
        return GroveTemperatureHumiditySensor(sensor)
    }
}