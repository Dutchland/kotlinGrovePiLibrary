package nl.dutchland.grove.utility

data class FractionalPercentage private constructor(val percentage: Double) {
    val fraction : Double = this.percentage / 100.0

    init {
        Conditions.assertNotLargerThan(percentage, 100.0)
        {  handleInvalidFraction("A fractional percentage cannot be larger than 100%: $percentage") }
        Conditions.assertNotNegative(percentage)
        { handleInvalidFraction("A percentage cannot be negative: $percentage") }
    }

    companion object {
        fun ofPercentage(percentage : Double) : FractionalPercentage {
           return FractionalPercentage(percentage)
        }

        fun ofFraction(fraction : Double) : FractionalPercentage {
            Conditions.assertNotLargerThan(fraction, 1.0)
            {  handleInvalidFraction("A fraction cannot be larger than 1.0: $fraction") }
            Conditions.assertNotNegative(fraction)
            { handleInvalidFraction("A fraction cannot be negative: $fraction") }

            return FractionalPercentage(fraction * 100.0)
        }

        private fun handleInvalidFraction(errorMessage: String) {
            throw InvalidFractionException(errorMessage)
        }

        val ZERO = FractionalPercentage.ofFraction(0.0)
        val HUNDRED_PERCENT = FractionalPercentage.ofFraction(1.0)
    }
}

class InvalidFractionException(errorMessage: String) : IllegalArgumentException (errorMessage)
