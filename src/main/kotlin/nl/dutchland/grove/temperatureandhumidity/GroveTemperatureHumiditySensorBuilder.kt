package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor

enum class GroveTemperatureHumiditySensors(val type: GroveTemperatureAndHumiditySensor.Type) {
    DHT11(GroveTemperatureAndHumiditySensor.Type.DHT11),
    DHT22(GroveTemperatureAndHumiditySensor.Type.DHT22)
}

class GroveTemperatureHumiditySensorBuilder(private val grovePi: GrovePi) {
    fun onPort(port: DigitalPort): TypeSetter {
        return object : TypeSetter {
            override fun withType(type: GroveTemperatureHumiditySensors): ListenerSetter {
                return withTypeA(type, port)
            }
        }
    }

    private fun withTypeA(type: GroveTemperatureHumiditySensors, port: DigitalPort): ListenerSetter {
        return object : ListenerSetter {
            override fun withListener(listener: TemperatureHumidityListener): Builder {
                return withListenerA(listener, port, type)
            }

            override fun withTemperatureListener(listener: TemperatureListener): Builder {
                return withListenerA({ th -> listener.invoke(th.toTemperatureMeasurement()) }, port, type)
            }

            override fun withHumidityListener(listener: HumidityListener): Builder {
                return withListenerA({ th -> listener.invoke(th.toHumidityMeasurement()) }, port, type)
            }
        }
    }

    private fun withListenerA(listener: TemperatureHumidityListener, port: DigitalPort, type: GroveTemperatureHumiditySensors): Builder {
        return object : Builder {
            override fun build(): TemperatureHumiditySensor {
                return builda(port, type, listener)
            }
        }
    }

    private fun builda(port: DigitalPort, type: GroveTemperatureHumiditySensors, listener: TemperatureHumidityListener): TemperatureHumiditySensor {
        val sensor = GroveTemperatureAndHumiditySensor(grovePi, port.digitalPin, type.type)
        return GroveTemperatureHumiditySensor(sensor, listener)
    }

    interface TypeSetter {
        fun withType(type: GroveTemperatureHumiditySensors): ListenerSetter
    }

    interface ListenerSetter {
        fun withListener(listener: TemperatureHumidityListener): Builder
        fun withTemperatureListener(listener: TemperatureListener): Builder
        fun withHumidityListener(listener: HumidityListener): Builder
    }

    interface Builder {
        fun build(): TemperatureHumiditySensor
    }
}




