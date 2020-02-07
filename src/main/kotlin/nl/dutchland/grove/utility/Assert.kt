package nl.dutchland.grove.utility

typealias AssertionFailedHandler = () -> Unit

fun Number.assertNotLargerThan(reference: Number, assertionFailedHandler: AssertionFailedHandler) {
    if (this.toDouble() > reference.toDouble()) {
        assertionFailedHandler.invoke()
    }
}

fun Number.assertNotNegative(assertionFailedHandler: AssertionFailedHandler) {
    if (this.toDouble() < 0.0) {
        assertionFailedHandler.invoke()
    }
}

fun Number.assertLargerThanZero(assertionFailedHandler: AssertionFailedHandler) {
    if (this.toDouble() <= 0.0) {
        assertionFailedHandler.invoke()
    }
}

fun Number.assertLargerOrEquals(reference: Number, assertionFailedHandler: AssertionFailedHandler) {
    if (this.toDouble() < reference.toDouble()) {
        assertionFailedHandler.invoke()
    }
}