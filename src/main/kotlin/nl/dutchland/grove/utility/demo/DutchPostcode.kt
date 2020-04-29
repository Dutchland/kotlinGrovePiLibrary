package nl.dutchland.grove.utility.demo

private const val POSTCODE_REGEX = "[1-9][0-9]{3}[A-Z]{2}"

class DutchPostcode(postcode: String) {
    private val value = postcode // Format: 1234AB
            .replace("\\s".toRegex(), "")
            .toUpperCase()

    val areaCode = value.take(4)
    val streetCode = value.takeLast(2)

    init {
        validate()
    }

    private fun validate() {
        val isValidPostCode = POSTCODE_REGEX.toRegex()
                .containsMatchIn(value)

        if (!isValidPostCode) {
            throw IllegalArgumentException("Not a valid Postcode: $value")
        }
    }
}