package nl.dutchland.grove.button

interface Button {
    fun addStatusChangedListener(listener: (Boolean) -> Unit)
    fun isPressed() : Boolean
}