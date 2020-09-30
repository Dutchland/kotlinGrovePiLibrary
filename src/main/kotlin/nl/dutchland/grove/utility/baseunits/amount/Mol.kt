package nl.dutchland.grove.utility.baseunits.amount

object Mol : AmountOfSubstance.Unit {
    override fun toMol(value: Double): Double = value

    override fun fromMol(valueInMol: Double): Double = valueInMol

    override val shortName: String = "mol"
    override val longName: String = "Mole"

    override fun toString(): String {
        return longName
    }
}