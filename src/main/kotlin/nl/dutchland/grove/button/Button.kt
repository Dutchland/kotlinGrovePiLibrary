package nl.dutchland.grove.button

typealias ButtonStatusChangedListener = (Boolean) -> Unit

interface Button {
    fun addStatusChangedListener(listener: ButtonStatusChangedListener)
    fun isPressed() : Boolean
}