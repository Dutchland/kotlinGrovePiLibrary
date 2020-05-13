package nl.dutchland.grove.sudoku

import nl.dutchland.grove.events.EventBus
import kotlin.properties.Delegates.observable

typealias CellValueChangedListener = (CellCoordinate, Int?) -> Unit

class Cell(val coordinate: CellCoordinate, private val eventBus: EventBus) {
    var value by observable<Int?>(null) { _, _, newValue ->
        eventBus.post(CellValueChangedEvent(coordinate, newValue))
    }

    val possibleValues = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    init {
        eventBus.subscribeWithFilter<CellValueChangedEvent>(
                { it.coordinate.hasOverlap(coordinate) },
                {
//                    possibleValues.remove(it.newValue)
                    if (possibleValues.size == 1) {
                        value = possibleValues[0]
                    }
                })
    }

//    private fun handle
}