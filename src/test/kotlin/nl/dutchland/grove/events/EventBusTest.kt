package nl.dutchland.grove.events

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
        val filter: EventFilter<SomeEvent> = { e -> e.someProperty.equals("CorrectProperty") }

        eventBus.subscribeWithFilter<SomeEvent> (
                filter,
                { someEvent -> someEventHandler.accept(someEvent) })

        //Act
        val matchingEvent = SomeEvent("CorrectProperty")
        eventBus.post(matchingEvent)

        val nonMatchingEvent = SomeEvent("WrongProperty")
        eventBus.post(nonMatchingEvent)

        // Assert
        verify(someEventHandler).accept(matchingEvent)
        verify(someEventHandler, times(0)).accept(nonMatchingEvent)
    }

    private class SomeEvent(val someProperty: String = ""): Event
}

