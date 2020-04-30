package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.grovepiports.DigitalPort
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensors.*
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

enum class GroveTemperatureHumiditySensors(val type: GroveTemperatureAndHumiditySensor.Type) {
    DHT11(GroveTemperatureAndHumiditySensor.Type.DHT11),
    DHT22(GroveTemperatureAndHumiditySensor.Type.DHT22)
}


class GroveTemperatureHumiditySensorBuilder(private val grovePi: GrovePi) {
    fun on(port: DigitalPort, type: GroveTemperatureHumiditySensors, listener: TemperatureHumidityListener): TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.digitalPin, type.type)
        return GroveTemperatureHumiditySensor(sensor, listener)
    }
    
    var port: DigitalPort = nl.dutchland.grove.grovepiports.GrovePi.D5
    var listener: TemperatureHumidityListener = {}
    var type: GroveTemperatureHumiditySensors = DHT11

    private fun withTypeA(type: GroveTemperatureHumiditySensors) : TemperatureHumiditySensorListenerSetter {
        this.type = type
        return object : TemperatureHumiditySensorListenerSetter {
            override fun withListener(listener: TemperatureHumidityListener): TemperatureHumiditySensorBuilder {
                return withListenerA(listener)
            }

            override fun withTemperatureListener(listener: TemperatureListener): TemperatureHumiditySensorBuilder {
                return withListenerA { th -> listener.invoke(th.toTemperatureMeasurement())}
            }

            override fun withHumidityListener(listener: HumidityListener): TemperatureHumiditySensorBuilder {
                return withListenerA { th -> listener.invoke(th.toHumidityMeasurement())}
            }
        }
    }

    fun onPort(port: DigitalPort) : TemperatureHumiditySensorTypeSetter {
        this.port = port
        return object : TemperatureHumiditySensorTypeSetter {
            override fun withType(type: GroveTemperatureHumiditySensors): TemperatureHumiditySensorListenerSetter {
                return withTypeA(type)
            }
        }
    }

    private fun withListenerA(listener: TemperatureHumidityListener) : TemperatureHumiditySensorBuilder  {
        this.listener = listener
        return object: TemperatureHumiditySensorBuilder {
            override fun build(): TemperatureHumiditySensor {
                return builda()
            }
        }
    }

    private fun builda(): TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.digitalPin, type.type)
        return GroveTemperatureHumiditySensor(sensor, listener)
    }
}



interface TemperatureHumiditySensorTypeSetter {
    fun withType(type: GroveTemperatureHumiditySensors) : TemperatureHumiditySensorListenerSetter
}

interface TemperatureHumiditySensorListenerSetter {
    fun withListener(listener: TemperatureHumidityListener) : TemperatureHumiditySensorBuilder
    fun withTemperatureListener(listener: TemperatureListener) : TemperatureHumiditySensorBuilder
    fun withHumidityListener(listener: HumidityListener) : TemperatureHumiditySensorBuilder
}

//interface TemperatureHumiditySensorPortSetter {
//    fun withPort(port: DigitalPort) : TemperatureHumiditySensorBuilder
//}

interface TemperatureHumiditySensorBuilder {
    fun build() : TemperatureHumiditySensor
}
