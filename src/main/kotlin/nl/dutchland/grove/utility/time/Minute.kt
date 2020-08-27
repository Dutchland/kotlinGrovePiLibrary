package nl.dutchland.grove.utility.time

object Minute : Period.TimeUnit() {
    private const val MINUTE_TO_SECOND_FACTOR = 60.0

    override val longName = "Minute"
    override val shortName = "min"

    override fun fromSeconds(valueInSeconds: Double): Double =
            valueInSeconds / MINUTE_TO_SECOND_FACTOR

    override fun toSeconds(value: Double): Double =
            value * MINUTE_TO_SECOND_FACTOR
}