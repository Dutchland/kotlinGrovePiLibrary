package nl.dutchland.grove.utility.baseunits.amount

import nl.dutchland.grove.utility.derivedunits.amountofsubstanceconcentration.AmountOfSubstanceConcentration
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume

private typealias AmountOfMolProvider = () -> Double

class AmountOfSubstance internal constructor(private val amountOfMolProvider: AmountOfMolProvider) {
    companion object {
        fun of(amount: Double, unit: AmountOfSubstance.Unit): AmountOfSubstance {
            return AmountOfSubstance { unit.toMol(amount) }
        }
    }

    fun valueIn(unit: AmountOfSubstance.Unit): Double {
        return unit.fromMol(amountOfMolProvider.invoke())
    }

    operator fun plus(other: AmountOfSubstance): AmountOfSubstance {
        return AmountOfSubstance { this.amountOfMolProvider.invoke() + other.amountOfMolProvider.invoke() }
    }

    interface Unit {
        fun toMol(value: Double): Double
        fun fromMol(valueInMol: Double): Double

        val shortName: String
        val longName: String

        override fun toString(): String

        operator fun div(volumeUnit: Volume.Unit): AmountOfSubstanceConcentration.Unit {
            TODO("Not yet implemented")
        }
    }
}