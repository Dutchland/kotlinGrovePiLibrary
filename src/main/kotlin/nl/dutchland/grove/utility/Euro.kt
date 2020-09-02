package nl.dutchland.grove.utility

data class Euro internal constructor(private val amountInCentsProvider: () -> Long)
    : Comparable<Euro> {

    val priceInCents: Long = amountInCentsProvider.invoke()
    val priceInEuro: Double = amountInCentsProvider.invoke() / 100.0

    companion object {
        fun fromCents(amountInCents: () -> Long): Euro {
            return Euro(amountInCents)
        }

        fun fromCents(amountInCents: Long): Euro {
            return Euro { amountInCents }
        }
    }

    operator fun times(valueIn: Double): Euro {
        TODO("Not yet implemented")
    }

    override operator fun compareTo(other: Euro): Int {
        return priceInCents.compareTo(other.priceInCents)
    }

    fun print() : String{
        return "$priceInEuro Euro"
    }
}