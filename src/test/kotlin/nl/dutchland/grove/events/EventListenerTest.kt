package nl.dutchland.grove.events

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test
import java.util.function.Consumer

internal class EventListenerTest {

    @Test
    fun test() {
        // Arrange
        val someEventHandler = mock<Consumer<SomeEvent>>()

        val eventListener = EventListener(SomeEvent::class, { true }, { someEvent ->
            someEventHandler.accept(someEvent)
        })

        // Act
        val event = SomeEvent()
        eventListener.onEvent(event)

        // Assert
        verify(someEventHandler).accept(event)
    }

    @Test
    fun testFilter() {
        // Arrange
        val someEventHandler = mock<Consumer<SomeEvent>>()
        val filter: EventFilter<SomeEvent> = { e -> e.someProperty.equals("SomeOtherText") }

        val eventListener = EventListener(SomeEvent::class, filter, { someEvent ->
            someEventHandler.accept(someEvent)
        })

        // Act
        val nonMatchingEvent = SomeEvent("NotTheCorrectText")
        eventListener.onEvent(nonMatchingEvent)

        val matchingEvent = SomeEvent("SomeOtherText")
        eventListener.onEvent(matchingEvent)

        // Assert
        verify(someEventHandler).accept(matchingEvent)
        verify(someEventHandler, times(0)).accept(nonMatchingEvent)
    }

    @Test
    fun subclassesOfSubscribedEventsTriggerTo() {
        // Arrange
        val someEventHandler = mock<Consumer<SomeEvent>>()

        val eventListener = EventListener(SomeEvent::class, { true }, { someEvent ->
            someEventHandler.accept(someEvent)
        })

        // Act
        val subclassEvent = SubclassOfSomeEvent()
        eventListener.onEvent(subclassEvent)

        val event = SomeEvent()
        eventListener.onEvent(event)

        // Assert
        verify(someEventHandler).accept(subclassEvent)
        verify(someEventHandler).accept(event)
    }

    @Test
    fun unrelatedEventsDontTrigger() {
        // Arrange
        val someEventHandler = mock<Consumer<SomeEvent>>()

        val eventListener = EventListener(SomeEvent::class, { true }, { someEvent ->
            someEventHandler.accept(someEvent)
        })

        // Act
        eventListener.onEvent(UnrelatedEvent())

        // Assert
        verify(someEventHandler, times(0)).accept(any())
    }

    private open class SomeEvent(val someProperty: String = "SomeText") : Event

    private class SubclassOfSomeEvent : SomeEvent()

    private class UnrelatedEvent : Event
}