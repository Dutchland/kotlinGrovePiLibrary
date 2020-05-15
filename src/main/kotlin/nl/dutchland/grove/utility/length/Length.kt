package nl.dutchland.grove.utility.length

data class Length private constructor(private val lengthInMillimeters: Double) {
    companion object {
        fun of(value: Double, unit: Unit): Length {
            return Length(unit.toMillimeter(value))
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromMillimeter(lengthInMillimeters)
    }

    operator fun compareTo(other: Length): Int {
        return this.lengthInMillimeters.compareTo(other.lengthInMillimeters)
    }

    operator fun plus(other: Length): Length {
        return Length(this.lengthInMillimeters + other.lengthInMillimeters)
    }

    operator fun minus(other: Length): Length {
        return Length(this.lengthInMillimeters - other.lengthInMillimeters)
    }

    abstract class Unit {
        abstract fun fromMillimeter(valueInMillimeter: Double): Double
        abstract fun toMillimeter(value: Double): Double
        abstract val name: String

        override fun toString(): String {
            return name
        }
    }
}