package nl.dutchland.grove.utility

typealias AssertionFailedHandler = () -> Unit

object Assert {

    fun notLargerThan(value: Number, reference: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() > reference.toDouble()) {
            assertionFailedHandler.invoke()
        }
    }

    fun notNegative(value: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() < 0.0) {
            assertionFailedHandler.invoke()
        }
    }

    fun largerThanZero(value: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() <= 0.0) {
            assertionFailedHandler.invoke()
        }
    }

    fun largerOrEquals(value: Number, reference: Number, assertionFailedHandler: AssertionFailedHandler) {
        if (value.toDouble() < reference.toDouble()) {
            assertionFailedHandler.invoke()
        }
    }
}