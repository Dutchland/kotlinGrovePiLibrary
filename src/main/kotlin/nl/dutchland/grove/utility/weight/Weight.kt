package nl.dutchland.grove.utility.weight

data class Weight private constructor(private val weightInGrams: Double) {
    companion object {
        fun of(value: Double, scale: Scale): Weight {
            return Weight(scale.toGrams(value))
        }
    }

    fun valueIn(scale: Scale): Double {
        return scale.fromGrams(weightInGrams)
    }

    operator fun compareTo(other: Weight): Int {
        return this.weightInGrams.compareTo(other.weightInGrams)
    }

    operator fun plus(other: Weight): Weight {
        return Weight(this.weightInGrams + other.weightInGrams)
    }

    operator fun minus(other: Weight): Weight {
        return Weight(this.weightInGrams - other.weightInGrams)
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