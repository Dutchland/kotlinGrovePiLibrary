package testutility

import com.natpryce.hamkrest.Matcher
import kotlin.reflect.KClass
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.opentest4j.AssertionFailedError

typealias ExceptionThrower = () -> Unit

class ExceptionAssert private constructor(private val exception: Exception) {
    companion object {
        fun assertThrows(function: ExceptionThrower): ExceptionAssert {
            try {
                function.invoke()
            } catch (e: Exception) {
                return ExceptionAssert(e)
            }

            throw fail<AssertionError>("Expected an exception to be thrown, but there was none")
        }

        fun assertNotThrows(function: ExceptionThrower) {
            try {
                function.invoke()
            } catch (e: Exception) {
                fail<Unit>("Asserted not to throw an exception but did: $e")
            }
        }
    }

    fun assertExactExceptionType(expectedType: KClass<out Exception>) =
            apply {
                val actualType = exception::class
                assertEquals(expectedType, actualType, "Was different type")
            }

    fun assertExactExceptionType(expectedType: Class<out Exception>) =
            apply {
                val actualType = exception::class.java
                assertEquals(expectedType, actualType, "Was different type")
            }

    fun assertExceptionMessage(expectedMessage: String) =
            apply {
                val actualMessage = this.exception.message
                        ?: throw AssertionFailedError("Exception did not have any error message")

                assertEquals(expectedMessage, actualMessage,
                        "Expected expectedMessage: \"$expectedMessage\" but got: \"$actualMessage\"")
            }

    fun assertExceptionMessage(matcher: Matcher<String>) =
            apply {
                val actualMessage = this.exception.message
                        ?: throw AssertionFailedError("Exception did not have any error message")

                val matches  = matcher.asPredicate()
                        .invoke(actualMessage)
                when {
                    !matches -> {
                        throw AssertionFailedError("Errormessage: '$actualMessage' did not match the matcher")
                    }
                }
            }
}
