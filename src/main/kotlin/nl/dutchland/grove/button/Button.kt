package nl.dutchland.grove.button

interface ButtonStatusChangedListener {
    fun onStatusChanged(newStatus: ButtonStatus)
}

interface Button {
    val status: ButtonStatus
}

enum class ButtonStatus {
    PRESSED,
    NOT_PRESSED
}