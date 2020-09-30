package nl.dutchland.grove.utility.baseunits.luminousintensity

typealias cd = Candela
object Candela : LuminousIntensity.Unit {
    override val shortName: String = "cd"
    override val longName: String = "Candela"

    override fun fromCandela(valueInCandela: Double): Double = valueInCandela
    override fun toCandela(value: Double): Double = value

    override fun toString(): String = longName
}