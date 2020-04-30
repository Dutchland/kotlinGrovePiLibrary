package nl.dutchland.grove.events

typealias EventHandler<T> = (T) -> Unit
typealias EventFilter<T> = (T) -> Boolean

class EventBus() {
    private val listeners = mutableListOf<EventListener<*>>()

    fun <T : Event> subscribe(eventListener: EventListener<T>) {
        listeners += (eventListener)
    }

    inline fun <reified T : Event> subscribe(noinline eventHandler: EventHandler<T>) {
        subscribeWithFilter({ true }, eventHandler)
    }

    inline fun <reified T : Event> subscribeWithFilter(noinline filter: EventFilter<T>, noinline eventHandler: EventHandler<T>) {
        subscribe(EventListener(
                clazz = T::class,
                eventFilter = filter,
                eventHandler = eventHandler))
    }

    fun post(event: Event) {
        listeners.forEach { eventListener ->
            eventListener.onEvent(event)
        }
    }
}