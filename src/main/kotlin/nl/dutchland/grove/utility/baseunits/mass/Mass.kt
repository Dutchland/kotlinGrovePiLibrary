package nl.dutchland.grove.utility.baseunits.mass

import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.massflow.MassFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.mechanical.massdensity.div
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.Volume
import nl.dutchland.grove.utility.derivedunits.mechanical.volume.m3

class Mass private constructor(private val value: Double, private val unit: Mass.Unit) : Comparable<Mass> {
    private val massInKilograms: Double by lazy {
        unit.toKiloGrams(value)
    }

    companion object {
        fun of(value: Double, unit: Mass.Unit): Mass {
            return Mass(value, unit)
        }
    }

    fun valueIn(unit: Mass.Unit): Double {
        if (this.unit.equals(unit)) {
            return this.value
        }
        return unit.fromKilograms(massInKilograms)
    }

    override operator fun compareTo(other: Mass): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }

        return this.massInKilograms.compareTo(other.massInKilograms)
    }

    operator fun plus(other: Mass): Mass {
        if (unit == other.unit) {
            return Mass(this.value + other.value, this.unit)
        }
        return Mass(other.massInKilograms + massInKilograms, Kilogram)
    }

    operator fun minus(other: Mass): Mass {
        if (unit == other.unit) {
            return Mass(this.value - other.value, this.unit)
        }
        return Mass(this.massInKilograms - other.massInKilograms, Kilogram)
    }

    operator fun times(factor: Double): Mass {
        return Mass(value * factor, unit)
    }

    operator fun div(volumeUnit: Volume) : MassDensity {
        return MassDensity.of(this.massInKilograms / volumeUnit.valueIn(m3), kg/m3)
    }

    operator fun div(massDensity: MassDensity): Volume {
        TODO("Not yet implemented")
    }

    operator fun div(volumeUnit: Volume.Unit): MassDensity {
        return MassDensity.of(this.massInKilograms, kg/volumeUnit)
    }

    operator fun div(other: Mass): Double {
        return this.massInKilograms / other.massInKilograms
    }

    abstract class Unit {
        abstract fun fromKilograms(valueInKilogram: Double): Double
        abstract fun toKiloGrams(value: Double): Double
        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }

        operator fun div(unit: Volume.Unit): MassDensity.Unit {
            return MassDensity.Unit.parameterized(this, unit)
        }

        operator fun div(timeUnit: Time.Unit): MassFlow.Unit {
            TODO("Not yet implemented")
        }
    }
}

fun Iterable<Mass>.sum(): Mass {
    return this.fold(Mass.of(0.0, Gram)) { w1, w2 -> w1 + w2 }
}