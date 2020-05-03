package nl.dutchland.grove.events

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test
import java.util.function.Consumer

internal class EventBusTest {

    @Test
    fun testMultipleSubscribers() {
        // Arrange
        val eventBus = EventBus()

        val someEventHandler1 = mock<Consumer<SomeEvent>>()
        val someEventHandler2 = mock<Consumer<SomeEvent>>()
        eventBus.subscribe<SomeEvent> { someEvent -> someEventHandler1.accept(someEvent) }
        eventBus.subscribe<SomeEvent> { someEvent -> someEventHandler2.accept(someEvent) }

        //Act
        val event = SomeEvent()
        eventBus.post(event)

        // Assert
        verify(someEventHandler1).accept(event)
        verify(someEventHandler2).accept(event)
    }

    @Test
    fun testWithFilter() {
        // Arrange
        val eventBus = EventBus()
        val someEventHandler = mock<Consumer<SomeEvent>>()

        eventBus.subscribeWithFilter<SomeEvent>(
                { it.someProperty.equals("CorrectProperty") },
                { someEventHandler.accept(it) })

        //Act
        val matchingEvent = SomeEvent("CorrectProperty")
        eventBus.post(matchingEvent)

        val nonMatchingEvent = SomeEvent("WrongProperty")
        eventBus.post(nonMatchingEvent)

        // Assert
        verify(someEventHandler).accept(matchingEvent)
        verify(someEventHandler, times(0)).accept(nonMatchingEvent)
    }

    @Test
    fun `Handeling child events and unrelated events`() {
        // Arrange
        val eventBus = EventBus()
        val someEventHandler = mock<Consumer<SomeEvent>>()
        val childEventHandler = mock<Consumer<SomeChildEvent>>()
        val unRelatedEventHandler = mock<Consumer<UnrelatedEvent>>()

        eventBus.subscribe<SomeEvent> { someEventHandler.accept(it) }
        eventBus.subscribe<SomeChildEvent> { childEventHandler.accept(it) }
        eventBus.subscribe<UnrelatedEvent> { unRelatedEventHandler.accept(it) }

        // Act
        eventBus.post(SomeEvent())
        eventBus.post(SomeChildEvent())
        eventBus.post(UnrelatedEvent())

        // Assert
        verify(someEventHandler, times(2)).accept(any())
        verify(childEventHandler).accept(any())
        verify(unRelatedEventHandler).accept(any())
    }

    private class UnrelatedEvent : Event
    private open class SomeEvent(val someProperty: String = "") : Event
    private class SomeChildEvent() : SomeEvent()
}

