package nl.dutchland.grove.utility.time

object Minute : Period.TimeUnit {
    private const val MINUTE_TO_SECOND_FACTOR = 60.0

    override fun fromSeconds(value: Double): Double {
        return value / MINUTE_TO_SECOND_FACTOR
    }

    override val name = "Minute"

    override fun toSeconds(value: Double): Double {
        return value * MINUTE_TO_SECOND_FACTOR
    }
}