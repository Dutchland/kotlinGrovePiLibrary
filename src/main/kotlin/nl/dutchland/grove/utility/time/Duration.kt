package nl.dutchland.grove.utility.time

import nl.dutchland.grove.utility.Conditions

data class Duration private constructor(private val seconds : Double) {
    companion object {
        fun of(value: Double, scale: TimeScale) : Duration {
            Conditions.assertLargerThanZero(value) { throw InvalidIntervalException("Duration cannot be negative") }
            return Duration(scale.toSeconds(value))
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
