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
    private lateinit var port: DigitalPort
    private lateinit var listener: TemperatureHumidityListener
    private lateinit var type: GroveTemperatureHumiditySensors

    fun onPort(port: DigitalPort) : TemperatureHumiditySensorTypeSetter {
        this.port = port
        return object : TemperatureHumiditySensorTypeSetter {
            override fun withType(type: GroveTemperatureHumiditySensors): TemperatureHumiditySensorListenerSetter {
                return withTypeA(type)
            }
        }
    }

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

    interface TemperatureHumiditySensorTypeSetter {
        fun withType(type: GroveTemperatureHumiditySensors) : TemperatureHumiditySensorListenerSetter
    }

    interface TemperatureHumiditySensorListenerSetter {
        fun withListener(listener: TemperatureHumidityListener) : TemperatureHumiditySensorBuilder
        fun withTemperatureListener(listener: TemperatureListener) : TemperatureHumiditySensorBuilder
        fun withHumidityListener(listener: HumidityListener) : TemperatureHumiditySensorBuilder
    }

    interface TemperatureHumiditySensorBuilder {
        fun build() : TemperatureHumiditySensor
    }
}




