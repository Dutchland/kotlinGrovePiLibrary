package nl.dutchland.grove.events

class EventListener<T : Event>(
        private val eventFilter: EventFilter<T>,
        private val eventHandler: EventHandler<T>) {

    fun onEvent(event: T) {
        if (eventFilter.invoke(event)) {
            eventHandler.invoke(event)
        }
    }
}