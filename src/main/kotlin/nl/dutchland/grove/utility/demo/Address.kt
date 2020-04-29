package nl.dutchland.grove.utility.demo

data class Address(
        val city: String,
        val postcode: DutchPostcode,
        val street: String,
        val housenumber: Housenumber // Number + addition
)