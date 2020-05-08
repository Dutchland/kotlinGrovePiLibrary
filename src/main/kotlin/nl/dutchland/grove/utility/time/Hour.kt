package nl.dutchland.grove.utility.time

object Hour : Period.TimeScale {
    private const val HOUR_TO_SECOND_FACTOR = 60.0 * 60.0

    override fun fromSeconds(value: Double): Double {
        return value / HOUR_TO_SECOND_FACTOR
    }

    override val name = "Hour"

    override fun toSeconds(value: Double): Double {
        return value * HOUR_TO_SECOND_FACTOR
    }
}