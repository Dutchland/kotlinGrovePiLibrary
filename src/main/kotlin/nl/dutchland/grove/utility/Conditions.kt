package nl.dutchland.grove.utility

typealias AssertionFailedHandler = () -> Unit

object Conditions {

    fun assertNotLargerThan(value: Number, reference: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() > reference.toDouble()) {
            assertionFailedHandler.invoke()
        }
    }

    fun assertNotNegative(value: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() < 0.0) {
            assertionFailedHandler.invoke()
        }
    }

    fun assertLargerThanZero(value: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() <= 0.0) {
            assertionFailedHandler.invoke()
        }
    }
}