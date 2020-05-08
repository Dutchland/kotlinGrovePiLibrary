package nl.dutchland.grove.sudoku

import nl.dutchland.grove.events.Event

class CellValueChangedEvent(val coordinate: CellCoordinate, val newValue: Int?) : Event