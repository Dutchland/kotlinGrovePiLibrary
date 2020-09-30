package nl.dutchland.grove.utility.derivedunits.force

typealias N = Newton
object Newton : Force.Unit {
    override val shortName: String = "N"
    override val longName: String = "Newton"

    override fun fromNewton(valueInNewton: Double): Double = valueInNewton
    override fun toNewton(value: Double): Double = value

    override fun toString(): String = longName
}