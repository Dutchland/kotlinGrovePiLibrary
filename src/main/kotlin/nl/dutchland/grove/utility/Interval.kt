package nl.dutchland.grove.utility

class Interval private constructor(val inMilliseconds: Long) {
    companion object {
        fun inMilliseconds(milliseconds: Long) : Interval {
            Conditions.assertLargerThanZero(milliseconds){ throw InvalidIntervalException("Interval cannot be negative: $milliseconds") }
            return Interval(milliseconds)
        }
    }
}

class InvalidIntervalException(message: String) : RuntimeException(message) {

}
