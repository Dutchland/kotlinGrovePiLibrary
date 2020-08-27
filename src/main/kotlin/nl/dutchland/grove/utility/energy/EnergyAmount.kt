package nl.dutchland.grove.utility.energy

typealias EnergyInJouleProvider = () -> Double

data class EnergyAmount internal constructor(private val energyInJouleProvider: EnergyInJouleProvider) : Comparable<EnergyAmount> {
    private val energyInJoule: Double by lazy {
        energyInJouleProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): EnergyAmount {
            return EnergyAmount { unit.toJoule(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromJoule(energyInJoule)
    }

    override operator fun compareTo(other: EnergyAmount): Int {
        return this.energyInJoule.compareTo(other.energyInJoule)
    }

    operator fun plus(other: EnergyAmount): EnergyAmount {
        return EnergyAmount { this.energyInJoule + other.energyInJoule }
    }

    operator fun minus(other: EnergyAmount): EnergyAmount {
        return EnergyAmount { this.energyInJoule - other.energyInJoule }
    }

    operator fun div(divider: Double): EnergyAmount {
        return EnergyAmount { this.energyInJoule / divider }
    }

    operator fun times(factor: Double): EnergyAmount {
        return EnergyAmount { this.energyInJoule * factor }
    }

    abstract class Unit {
        abstract fun fromJoule(valueInJoule: Double): Double
        abstract fun toJoule(value: Double): Double

        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }
    }
}