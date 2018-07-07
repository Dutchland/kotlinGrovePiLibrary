package nl.dutchland.grove.grovepiports

import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.fail

typealias ExceptionThrower = () -> Unit

class ExceptionAssert private constructor(private val exception: Exception) {
    companion object {
        fun assertThrows(function: ExceptionThrower): ExceptionAssert {
            try {
                function.invoke()
            } catch (e: Exception) {
                return ExceptionAssert(e)
            }

            fail("Expected an exception to be thrown, but there was none")
        }
    }

    fun assertExceptionType(expectedType: KClass<Exception>) {
        val actualType = exception::class
        assertEquals(expectedType, actualType, "Was different type")
    }

    fun assertExceptionMessage(expectedMessage : String) {
        val actualMessage = this.exception.message
        assertEquals(expectedMessage, actualMessage,
                "Expected expectedMessage: \"$expectedMessage\" but got: \"$actualMessage\"")
    }


}