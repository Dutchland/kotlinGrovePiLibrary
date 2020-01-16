package nl.dutchland.grove.utility

data class Fraction private constructor(val percentage: Double) : Comparable<Fraction> {
    val fraction : Double = this.percentage / 100.0

    init {
        Assert.notLargerThan(percentage, 100.0)
        {  handleInvalidFraction("A fractional percentage cannot be larger than 100%: $percentage") }
        Assert.notNegative(percentage)
        { handleInvalidFraction("A percentage cannot be negative: $percentage") }
    }

    companion object {
        fun ofPercentage(percentage : Double) : Fraction {
           return Fraction(percentage)
        }

        fun ofFraction(fraction : Double) : Fraction {
            Assert.notLargerThan(fraction, 1.0)
            {  handleInvalidFraction("A fraction cannot be larger than 1.0: $fraction") }
            Assert.notNegative(fraction)
            { handleInvalidFraction("A fraction cannot be negative: $fraction") }

            return Fraction(fraction * 100.0)
        }

        private fun handleInvalidFraction(errorMessage: String) {
            throw InvalidFractionException(errorMessage)
        }

        val ZERO = ofFraction(0.0)
        val HUNDRED_PERCENT = ofFraction(1.0)
    }

    override fun compareTo(other: Fraction): Int {
        return other.fraction.compareTo(this.fraction)
    }
}

class InvalidFractionException(errorMessage: String) : IllegalArgumentException (errorMessage)
