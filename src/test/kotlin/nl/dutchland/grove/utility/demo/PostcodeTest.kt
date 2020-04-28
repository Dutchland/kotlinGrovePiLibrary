package nl.dutchland.grove.utility.demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PostcodeTest {

    @Test
    fun testLazy() {
        val streetCode = Postcode("2624 VV").streetCode
        println(streetCode)
    }

}