package nl.dutchland.grove.utility.baseunits.time

object Hour : Period.TimeUnit() {
    private const val HOUR_TO_SECOND_FACTOR = 60.0 * 60.0

    override val longName = "Hour"
    override val shortName = "h"

    override fun fromSeconds(valueInSeconds: Double): Double =
            valueInSeconds / HOUR_TO_SECOND_FACTOR

    override fun toSeconds(value: Double): Double =
            value * HOUR_TO_SECOND_FACTOR
}