package nl.dutchland.grove.button

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.iot.raspberry.grovepi.GrovePi

class GroveButtonBuilder(private val grovePi: GrovePi) {
    fun on(port: DigitalPort): ListenerSetter {
        return object: ListenerSetter {
            override fun withListener(listener: ButtonStatusChangedListener): Builder {
                return setListener(port, listener)
            }

        }
    }

    private fun setListener(port: DigitalPort, listener: ButtonStatusChangedListener): Builder {
        return object : Builder {
            override fun build(): Button {
                return buildA(port,listener)
            }
        }
    }

    interface ListenerSetter {
        fun withListener(listener: ButtonStatusChangedListener): Builder

    }

    private fun buildA(port: DigitalPort, listener: ButtonStatusChangedListener) : Button {
        return GroveButton(
                GroveDigitalIn(this.grovePi, port.digitalPin), listener)
    }

    interface Builder {
        fun build(): Button
    }
}