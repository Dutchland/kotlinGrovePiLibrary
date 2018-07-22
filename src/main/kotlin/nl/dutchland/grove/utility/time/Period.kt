package nl.dutchland.grove.utility.time

import nl.dutchland.grove.utility.Conditions

data class Period private constructor(private val seconds : Double) {
    companion object {
        fun of(value: Double, scale: TimeScale) : Period {
            Conditions.assertLargerThanZero(value) { throw InvalidIntervalException("Period cannot be negative") }
            return Period(scale.toSeconds(value))
        }
    }

    fun valueIn(scale: TimeScale) : Double {
        return scale.fromSeconds(this.seconds)
    }

    interface TimeScale {
        fun toSeconds(value: Double) : Double
        fun fromSeconds(value: Double) : Double
    }
}

class InvalidIntervalException(message: String) : RuntimeException(message) {

}
