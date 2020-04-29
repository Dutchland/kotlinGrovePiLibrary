package nl.dutchland.grove

import kotlin.reflect.KClass

typealias EventHandler<T> = (T) -> Unit
//typealias EventFilter<T> = (T) -> Boolean

interface Event {

}

class TemperatureEvent : Event {
    val bla = 1
}

class EventBus() {
    private val listeners = mutableListOf<Any>()

    fun <T : Event> subscribe(eventListener: EventListener<T>) {
        listeners += (eventListener)
    }

//    fun <T : Event> subscribe(eventHandler: EventHandler<T>) {
//        subscribe<T>(EventListener(eventHandler))
//    }

    fun post(event: Event) {
        listeners.forEach { eventListener ->
            (eventListener as EventListener<*>)
                    .invoke(event)
        }
    }
}

typealias EventFilter = (Event) -> Boolean

fun <T : Event> forType(eventType: KClass<T>): EventFilter {
    return { event -> event::class == eventType }
}


//class EventFilter(filter: (Event) -> Boolean) : (Event) -> Boolean {
//    private val filter: (Event) -> Boolean
//
//    init {
//        this.filter = filter
//    }
//
//    companion object {
//        fun <T : Event> forType(eventType: KClass<T>) : EventFilter {
//            val filter: (Event) -> Boolean = { event -> event::class == eventType }
//            return EventFilter(filter)
//        }
//    }
//
//    override fun invoke(p1: Event): Boolean {
//        return filter.invoke(p1)
//    }
//}

class EventListener<T : Event>(
        private val eventFilter: EventFilter,
        private val eventHandler: EventHandler<T>) {

    constructor(eventHandler: EventHandler<T>) : this({ e: Event -> true }, eventHandler)

    fun invoke(event: Event) {
        if (eventFilter.invoke(event)) {
            eventHandler.invoke(event as T)
        }
    }
}