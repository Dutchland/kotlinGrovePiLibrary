package nl.dutchland.grove.utility

typealias ErrorHandler = () -> Unit

class Conditions {
    companion object {
        fun assertNotLargerThan(value: Double, reference: Double, errorHandler: ErrorHandler) {
            if (value > reference) {
                errorHandler.invoke()
            }
        }

        fun assertNotNegative(value: Double, errorHandler: ErrorHandler) {
            if (value < 0.0) {
                errorHandler.invoke()
            }
        }
    }
}