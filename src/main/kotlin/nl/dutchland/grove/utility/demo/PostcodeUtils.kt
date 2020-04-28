package nl.dutchland.grove.utility.demo

fun areaCode(postcode: String): String {
    checkValidPostcode(postcode)
    return postcode.take(4)
}

fun streetCode(postcode: String): String {
    checkValidPostcode(postcode)
    return postcode.take(2)
}

fun formatToDefaultPostcodeFormat(postcode: String): String {
    checkValidPostcode(postcode)
    return postcode
            .replace("\\s".toRegex(), "")
            .toUpperCase()
}

fun isValidPostcode(postcode: String): Boolean {
    TODO("Not yet implemented")
}

private fun checkValidPostcode(postcode: String) {
    TODO("Not yet implemented")
}
