package nl.dutchland.grove.demo

data class SimpleAddress(
        val city: String,
        val postcode: String,
        val street: String,
        val houseNumber: Int,
        val houseNumberAddition: String? = null
)