package nl.dutchland.grove.utility.derivedunits.energy

class Calorie : EnergyAmount.Unit {
    override fun fromJoule(valueInJoule: Double): Double {
        return valueInJoule / 4.18400
    }

    override fun toJoule(value: Double): Double {
        return value * 4.18400
    }

    override fun toString(): String {
        return "Joule"
    }

    override val shortName: String
        get() = TODO("Not yet implemented")
    override val longName: String
        get() = TODO("Not yet implemented")
}