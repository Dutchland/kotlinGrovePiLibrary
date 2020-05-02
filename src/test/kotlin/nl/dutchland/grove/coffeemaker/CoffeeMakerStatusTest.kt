package nl.dutchland.grove.coffeemaker

import com.nhaarman.mockito_kotlin.*
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.Fraction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.Test
import java.util.function.Consumer

internal class CoffeeMakerStatusTest {

    @Test
    fun `Starting the CoffeeMakerStatus should not post anything`() {
        // Arrange
        val eventBus = EventBus()
        val turnedOnHandler = mock<Consumer<CoffeeMakerTurnedOnEvent>>()
        val turnedOffHandler = mock<Consumer<CoffeeMakerTurnedOffEvent>>()
        eventBus.subscribe<CoffeeMakerTurnedOffEvent> { turnedOffHandler.accept(it) }
        eventBus.subscribe<CoffeeMakerTurnedOnEvent> { turnedOnHandler.accept(it) }

        // Act
        CoffeeMakerStatus(eventBus)

        // Assert
        assertAll(
                { verify(turnedOffHandler, times(0)).accept(any()) },
                { verify(turnedOnHandler, times(0)).accept(any()) }
        )
    }

    @Test
    fun `Posting a TryToTurnOnCoffeeMakerEvent should post that the coffeemaker is turned off`() {
        // Arrange
        val eventBus = EventBus()
        val turnedOnHandler = mock<Consumer<CoffeeMakerTurnedOnEvent>>()
        val turnedOffHandler = mock<Consumer<CoffeeMakerTurnedOffEvent>>()
        eventBus.subscribe<CoffeeMakerTurnedOffEvent> { turnedOffHandler.accept(it) }
        eventBus.subscribe<CoffeeMakerTurnedOnEvent> { turnedOnHandler.accept(it) }

        CoffeeMakerStatus(eventBus)

        // Act
        eventBus.post(TryToTurnOnCoffeeMakerEvent())

        // Assert
        val argumentCaptor = argumentCaptor<CoffeeMakerTurnedOffEvent>()
        assertAll(
                { verify(turnedOnHandler, times(0)).accept(any()) },
                { verify(turnedOffHandler).accept(argumentCaptor.capture()) },
                { assertTrue(argumentCaptor.firstValue.reasons.contains("the coffeepot is not in place")) },
                { assertTrue(argumentCaptor.firstValue.reasons.contains("there is no water in the tank")) }
        )
    }

    @Test
    fun `Posting all required events turns the coffeemaker on`() {
        // Arrange
        val eventBus = EventBus()
        val turnedOnHandler = mock<Consumer<CoffeeMakerTurnedOnEvent>>()
        val turnedOffHandler = mock<Consumer<CoffeeMakerTurnedOffEvent>>()
        eventBus.subscribe<CoffeeMakerTurnedOffEvent> { turnedOffHandler.accept(it) }
        eventBus.subscribe<CoffeeMakerTurnedOnEvent> { turnedOnHandler.accept(it) }

        CoffeeMakerStatus(eventBus)

        // Act
        eventBus.post(CoffeePotInPlaceEvent())
        eventBus.post(BoilerWaterLevelEvent(Fraction.of(0.5)))
        eventBus.post(TryToTurnOnCoffeeMakerEvent())

        // Assert
        assertAll(
                { verify(turnedOffHandler, times(0)).accept(any()) },
                { verify(turnedOnHandler).accept(any()) }
        )
    }
}