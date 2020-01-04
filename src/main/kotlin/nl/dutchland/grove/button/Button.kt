package nl.dutchland.grove.button

typealias ButtonStatusChangedListener = (ButtonStatus) -> Unit

interface Button {
    val status: ButtonStatus
}

enum class ButtonStatus(val isPressed: Boolean) {
    PRESSED(true),
    NOT_PRESSED(false)
}