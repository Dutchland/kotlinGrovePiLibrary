package nl.dutchland.grove.utility.demo

import org.junit.jupiter.api.Test

class PostcodeTest {

    @Test
    fun testLazy() {
        val streetCode = DutchPostcode("2624 VV").streetCode
        println(streetCode)
    }

}