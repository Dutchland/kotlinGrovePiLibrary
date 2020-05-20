package nl.dutchland.grove.demo

fun address() {
    val simpleAddress = SimpleAddress(
            "2624VV",
            "Delft",
            "Herculesweg",
            123,
            "A"
    )

    val simpleAddress1 = SimpleAddress(
            city = "2624VV",
            postcode = "Delft",
            street = "Herculesweg",
            houseNumber = 123,
            houseNumberAddition = "A"
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
    val validPostcode: String = PostcodeUtil.validateAndFormat(postcode) // validate
    val areaCode = PostcodeUtil.areaCode(validPostcode) // validate
    val streetCode = PostcodeUtil.streetCode(validPostcode) // validate

    if (areaCode.startsWith("26")) {
        println(validPostcode)
    }
}

fun postCode1(postcode: DutchPostcode) {  // validate
    postcode.areaCode
    postcode.streetCode
}

class PostcodeUtil {
    companion object {
        fun areaCode(postcode: String): String {
            validateAndFormat(postcode)
            return postcode.take(4)
        }

        fun streetCode(postcode: String): String {
            validateAndFormat(postcode)
            return postcode.takeLast(2)
        }

        fun validateAndFormat(postcode: String): String {
            TODO("Not yet implemented")
        }
    }
}
