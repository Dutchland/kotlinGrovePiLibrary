package nl.dutchland.grove.button

interface ButtonStatusChangedListener {
    fun onStatusChanged(isPressed: Boolean) : Void
}
