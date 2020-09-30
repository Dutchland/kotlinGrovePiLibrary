package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.derivedunits.pressure.Pressure

private const val TO_PASCAL_FACTOR = 101325

object Atmosphere : Pressure.Unit {
    override val shortName: String = "atm"
    override val longName: String = "Standard atmosphere"
    override fun fromPascal(valueInPascal: Double): Double = valueInPascal / TO_PASCAL_FACTOR

    override fun toPascal(value: Double): Double = value * TO_PASCAL_FACTOR

    override fun toString(): String = longName
}