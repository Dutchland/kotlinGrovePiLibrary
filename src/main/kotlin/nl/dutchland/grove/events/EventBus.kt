package nl.dutchland.grove.events

import kotlin.reflect.KClass

typealias EventHandler<T> = (T) -> Unit
typealias EventFilter<T> = (T) -> Boolean

class EventBus() {
    private val listenersMap : MutableMap<KClass<*>, MutableCollection<EventListener<*>>> = mutableMapOf()

    fun <T : Event> subscribe(clazz: KClass<T>, eventListener: EventListener<T>) {
        if (listenersMap.containsKey(clazz)) {
            listenersMap[clazz]?.add(eventListener)
        }

        listenersMap.putIfAbsent(clazz, mutableListOf(eventListener))
    }

    inline fun <reified T : Event> subscribe(noinline eventHandler: EventHandler<T>) {
        subscribeWithFilter({ true }, eventHandler)
    }

    inline fun <reified T : Event> subscribeWithFilter(noinline filter: EventFilter<T>, noinline eventHandler: EventHandler<T>) {
        subscribe(T::class, EventListener(
                eventFilter = filter,
                eventHandler = eventHandler))
    }

    fun <R : Event> post(event: R) {
        val listeners = listenersMap[event::class]

        listeners?.forEach { eventListener ->
            (eventListener as EventListener<R>).onEvent(event)
        }
    }
}