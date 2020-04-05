package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

class GroveTemperatureHumiditySensorFactory(private val grovePi : GrovePi) {
    fun createDHT11(port: DigitalPort) : TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.digitalPin, GroveTemperatureAndHumiditySensor.Type.DHT11)
        return GroveTemperatureHumiditySensor(sensor)
    }

    fun createDHT22(port: DigitalPort) : TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.digitalPin, GroveTemperatureAndHumiditySensor.Type.DHT22)
        return GroveTemperatureHumiditySensor(sensor)
    }
}