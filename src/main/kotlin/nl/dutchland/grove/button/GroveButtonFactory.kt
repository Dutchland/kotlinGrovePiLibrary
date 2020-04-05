package nl.dutchland.grove.button

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.iot.raspberry.grovepi.GrovePi

class GroveButtonFactory(private val grovePi: GrovePi) {
    private lateinit var port: DigitalPort
    private lateinit var listener: ButtonStatusChangedListener

    fun aButton(port: DigitalPort, vararg listeners: ButtonStatusChangedListener): GroveButton {
        return GroveButton(
                GroveDigitalIn(this.grovePi, port.digitalPin), listeners.asList())
    }

//    fun onPort(port: DigitalPort): ListenerSetter {
//        this.port = port
//        val impl = object : ListenerSetter {
//            override fun withListener(listener: ButtonStatusChangedListener): Builder {
//                return GroveButton()
//            }
//        }
//
//        val setter : (ButtonStatusChangedListener) -> Builder = { b  -> setListener(b) }
//        return setter
//    }


//    private fun setListener(listener: ButtonStatusChangedListener): Builder {
//        this.listener = listener
//    }

    private fun build(): Button {
        return GroveButton(
                GroveDigitalIn( grovePi, this.port.digitalPin), listOf(this.listener)
        )
    }

    interface ListenerSetter {
        fun withListener(listener: ButtonStatusChangedListener): Builder
    }

    interface Builder {
        fun build(): GroveButton
    }
}