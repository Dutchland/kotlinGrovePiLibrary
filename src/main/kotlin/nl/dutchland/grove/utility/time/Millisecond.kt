package nl.dutchland.grove.utility.time

object Millisecond : Duration.TimeScale {
    private val MILLI_TO_SECONDS = 1_000

    override fun toSeconds(value: Double): Double {
        return value / MILLI_TO_SECONDS
    }

    override fun fromSeconds(value: Double): Double {
        return value * MILLI_TO_SECONDS
    }
}