package nl.dutchland.grove.utility.baseunits.time

object Millisecond : Period.TimeUnit() {
    private const val MILLI_TO_SECONDS = 1_000

    override val longName = "Millisecond"
    override val shortName = "ms"

    override fun toSeconds(value: Double): Double =
            value / MILLI_TO_SECONDS

    override fun fromSeconds(valueInSeconds: Double): Double =
            valueInSeconds * MILLI_TO_SECONDS
}