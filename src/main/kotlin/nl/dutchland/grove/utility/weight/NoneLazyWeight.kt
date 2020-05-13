package nl.dutchland.grove.utility.weight

data class NoneLazyWeight private constructor(private val weightInGrams: Double) {
    companion object {
        fun of(value: Double, scale: Scale): NoneLazyWeight {
            return NoneLazyWeight(scale.toGrams(value))
        }
    }

    fun valueIn(scale: Scale): Double {
        return scale.fromGrams(weightInGrams)
    }

    operator fun compareTo(other: NoneLazyWeight): Int {
        return this.weightInGrams.compareTo(other.weightInGrams)
    }

    operator fun plus(other: NoneLazyWeight): NoneLazyWeight {
        return NoneLazyWeight(other.weightInGrams + weightInGrams)
    }

    operator fun minus(other: NoneLazyWeight): NoneLazyWeight {
        return NoneLazyWeight(this.weightInGrams - other.weightInGrams)
    }

    abstract class Scale {
        abstract fun fromGrams(valueInGrams: Double): Double
        abstract fun toGrams(value: Double): Double
        abstract val name: String

        override fun toString(): String {
            return name
        }
    }
}