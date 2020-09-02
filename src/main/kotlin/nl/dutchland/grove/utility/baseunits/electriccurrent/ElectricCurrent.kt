package nl.dutchland.grove.utility.baseunits.electriccurrent

class ElectricCurrent private constructor(private val value: Double, private val unit: ElectricCurrent.Unit) : Comparable<ElectricCurrent> {
    private val currentInAmpere: Double by lazy {
        unit.toAmpere(value)
    }

    companion object {
        fun of(value: Double, unit: ElectricCurrent.Unit): ElectricCurrent {
            return ElectricCurrent(value, unit)
        }
    }

    fun valueIn(unit: ElectricCurrent.Unit): Double {
        if (this.unit == unit) {
            return this.value
        }
        return unit.fromAmpere(currentInAmpere)
    }

    override operator fun compareTo(other: ElectricCurrent): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }
        return this.currentInAmpere.compareTo(other.currentInAmpere)
    }

    operator fun plus(other: ElectricCurrent): ElectricCurrent {
        if (unit == other.unit) {
            return ElectricCurrent(this.value + other.value, this.unit)
        }
        return ElectricCurrent(other.currentInAmpere + currentInAmpere, Ampere)
    }

    operator fun minus(other: ElectricCurrent): ElectricCurrent {
        if (unit == other.unit) {
            return ElectricCurrent(this.value - other.value, this.unit)
        }
        return ElectricCurrent(this.currentInAmpere - other.currentInAmpere, Ampere)
    }

    operator fun times(factor: Double): ElectricCurrent {
        return ElectricCurrent(value * factor, unit)
    }

    interface Unit {
        fun fromAmpere(valueInAmpere: Double): Double
        fun toAmpere(value: Double): Double
        val shortName: String
        val longName: String

        override fun toString(): String

        companion object {
            fun ofParameterized(longName: String, shortName: String, toAmpereFactor: Double): Unit {
                return ParameterizedUnit(longName, shortName, toAmpereFactor)
            }
        }
    }

    private class ParameterizedUnit(
            override val longName: String,
            override val shortName: String,
            private val toAmpereFactor: Double) : ElectricCurrent.Unit {

        override fun fromAmpere(valueInAmpere: Double): Double = valueInAmpere / toAmpereFactor
        override fun toAmpere(value: Double): Double = value * toAmpereFactor

        override fun toString(): String {
            return longName
        }
    }
}

fun Iterable<ElectricCurrent>.sum(): ElectricCurrent {
    return this.fold(ElectricCurrent.of(0.0, Ampere)) { w1, w2 -> w1 + w2 }
}