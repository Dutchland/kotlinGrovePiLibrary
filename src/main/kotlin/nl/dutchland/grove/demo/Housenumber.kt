package nl.dutchland.grove.demo

data class Housenumber(
        val houseNumber: Int,
        val appendix: String? = null) : Comparable<Housenumber> {

    override fun toString(): String {
        return "$houseNumber${appendix.orEmpty()}"
    }

    override fun compareTo(other: Housenumber): Int {
        TODO("Not yet implemented")
    }
}
