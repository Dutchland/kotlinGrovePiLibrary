package nl.dutchland.grove.utility.baseunits.amount

private typealias AmountOfMolProvider = () -> Double

class AmountOfMatter internal constructor(private val amountOfMolProvider: AmountOfMolProvider) {
    companion object {
        fun of(amount: Double, unit: AmountOfMatter.Unit): AmountOfMatter {
            return AmountOfMatter { unit.toMol(amount) }
        }
    }

    interface Unit {
        fun toMol(value: Double): Double
    }
}