package nl.dutchland.grove.utility.weight

private typealias WeightInGramsProvider = () -> Double

class Weight private constructor(private val weightInGramsProvider: WeightInGramsProvider) {
    private val weightInGrams: Double by lazy {
        println("calculating weight in grams")
        weightInGramsProvider.invoke()
    }

    companion object {
        fun of(value: Double, scale: Scale): Weight {
            return Weight { scale.toGrams(value) }
        }
    }

    fun valueIn(scale: Scale): Double {
        return scale.fromGrams(weightInGrams)
    }

    operator fun compareTo(other: Weight): Int {
        return this.weightInGrams.compareTo(other.weightInGrams)
    }

    operator fun plus(other: Weight): Weight {
        return Weight { other.weightInGrams + weightInGrams }
    }

    operator fun minus(other: Weight): Weight {
        return Weight { this.weightInGrams - other.weightInGrams }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weight

        if (weightInGrams != other.weightInGrams) return false

        return true
    }

    override fun hashCode(): Int {
        return weightInGrams.hashCode()
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