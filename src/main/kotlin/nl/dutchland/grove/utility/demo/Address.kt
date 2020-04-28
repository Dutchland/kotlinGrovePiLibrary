package nl.dutchland.grove.utility.demo

data class Address(
        val city: String,
        val postcode: Postcode,
        val street: String,
        val housenumber: Housenumber // Number + addition
)