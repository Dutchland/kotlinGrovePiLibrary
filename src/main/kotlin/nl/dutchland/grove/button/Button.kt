package nl.dutchland.grove.button

import nl.dutchland.grove.InputDevice

typealias ButtonStatusChangedListener = (ButtonStatus) -> Unit

interface Button : InputDevice {
    val status: ButtonStatus
}