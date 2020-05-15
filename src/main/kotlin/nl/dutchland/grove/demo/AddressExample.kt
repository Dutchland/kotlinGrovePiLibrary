package nl.dutchland.grove.demo

fun address() {
    val simpleAddress = SimpleAddress(
            "2624VV",
            "Delft",
            "Herculesweg",
            123,
            "A"
    )

    // Utility vs value objects
    val address = Address(
            "Delft",
            DutchPostcode("2624VV"),
            "Herculesweg",
            Housenumber(123, "A")
    )

//    val address2 = Address(
//            DutchPostcode("2624VV"),
//            "Delft",
//            Housenumber(123, "A"),
//            "Herculesweg"
//    )
}


fun postCode(postcode: String) {
    val validPostcode: String = validateAndFormat(postcode) // validate --> format
    PostcodeUtil.areaCode(validPostcode) // validate --> format
    PostcodeUtil.streetCode(validPostcode) // validate --> format
}

fun postCode1(postcode: DutchPostcode) {  // validate --> format
    postcode.areaCode
    postcode.streetCode
}

class PostcodeUtil {
    companion object {
        fun areaCode(postcode: String): String {
            //Validate --> Format
            return postcode.take(4)
        }

        fun streetCode(postcode: String): String {
            //Validate --> Format
            return postcode.takeLast(2)
        }
    }
}

fun validateAndFormat(postcode: String): String {
    TODO("Not yet implemented")
}
