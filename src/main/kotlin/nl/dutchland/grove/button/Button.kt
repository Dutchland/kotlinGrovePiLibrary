package nl.dutchland.grove.button

import nl.dutchland.grove.grovepiports.DigitalPort
import org.iot.raspberry.grovepi.GroveDigitalIn

interface ButtonStatusChangedListener {
    fun onStatusChanged(newStatus: ButtonStatus)
}

interface Button {
    val status: ButtonStatus
//
//    companion object {
//        fun onPort(port: DigitalPort): ListenerSetter {
//            val setter1: ListenerSetter = { () ->  }
//
////        this.port = port
////        val impl = object : ListenerSetter {
////            override fun withListener(listener: ButtonStatusChangedListener): Builder {
////                return GroveButton()
////            }
//        }
//
//        private fun build(): Button {
//            return GroveButton(
//                    GroveDigitalIn(grovePi, this.port.digitalPin), listOf(this.listener)
//            )
//        }
//    }
}

enum class ButtonStatus {
    PRESSED,
    NOT_PRESSED
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

interface ListenerSetter {
    fun withListener(listener: ButtonStatusChangedListener): Builder
}

interface Builder {
    fun build(): GroveButton
}