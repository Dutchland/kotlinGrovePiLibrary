package nl.dutchland.grove.utility.mass

class Mass private constructor(private val value: Double, private val unit: Mass.Unit) : Comparable<Mass> {
    private val weightInKilograms: Double by lazy {
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
        return unit.fromKilograms(weightInKilograms)
    }

    override operator fun compareTo(other: Mass): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }

        return this.weightInKilograms.compareTo(other.weightInKilograms)
    }

    operator fun plus(other: Mass): Mass {
        if (unit == other.unit) {
            return Mass(this.value + other.value, this.unit)
        }
        return Mass(other.weightInKilograms + weightInKilograms, Kilogram)
    }

    operator fun minus(other: Mass): Mass {
        if (unit == other.unit) {
            return Mass(this.value - other.value, this.unit)
        }
        return Mass(this.weightInKilograms - other.weightInKilograms, Kilogram)
    }

    operator fun times(factor: Double): Mass {
        return Mass(value * factor, unit)
    }

    abstract class Unit {
        abstract fun fromKilograms(valueInKilogram: Double): Double
        abstract fun toKiloGrams(value: Double): Double
        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<Mass>.sum(): Mass? {
    return this.fold(Mass.of(0.0, Gram)) { w1, w2 -> w1 + w2 }
}