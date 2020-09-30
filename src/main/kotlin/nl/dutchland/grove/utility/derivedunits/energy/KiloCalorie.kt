package nl.dutchland.grove.utility.derivedunits.energy

object KiloCalorie : EnergyAmount.Unit {
    override fun fromJoule(valueInJoule: Double): Double {
        return valueInJoule / 4184.00
    }

    override fun toJoule(value: Double): Double {
        return value * 4184.00
    }

    override fun toString(): String {
        return "KiloJoule"
    }

    override val shortName: String = "kJ"
    override val longName: String = "KiloJoule"
}