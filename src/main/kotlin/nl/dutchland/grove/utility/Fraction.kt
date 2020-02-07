package nl.dutchland.grove.utility

data class Fraction private constructor(val percentage: Double) : Comparable<Fraction> {
    val fraction: Double = this.percentage / 100.0

    init {
        percentage.assertNotLargerThan(100.0)
        { throw InvalidFractionException("A fractional percentage cannot be larger than 100%: $percentage") }
        percentage.assertNotNegative { throw InvalidFractionException("A percentage cannot be negative: $percentage") }
    }

    companion object {
        fun ofPercentage(percentage: Double): Fraction {
            return Fraction(percentage)
        }

        fun ofFraction(fraction: Double): Fraction {
            fraction.assertNotLargerThan(1.0)
            { throw InvalidFractionException("A fraction cannot be larger than 1.0: $fraction") }
            fraction.assertNotNegative { throw InvalidFractionException("A fraction cannot be negative: $fraction") }

            return Fraction(fraction * 100.0)
        }

        val ZERO = ofFraction(0.0)
        val HUNDRED_PERCENT = ofFraction(1.0)
    }

    override fun compareTo(other: Fraction): Int {
        return other.fraction.compareTo(this.fraction)
    }
}

class InvalidFractionException(errorMessage: String) : IllegalArgumentException(errorMessage)
