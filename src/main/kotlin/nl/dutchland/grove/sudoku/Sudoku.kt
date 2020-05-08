package nl.dutchland.grove.sudoku

import nl.dutchland.grove.events.EventBus


fun main() {
    val eventBus = EventBus()
    val cells = mutableListOf<Cell>()

    SubBlockName.values().forEach { subBlockName ->
        0.rangeTo(9).forEach { column ->
            0.rangeTo(9).forEach { row ->
                cells.add(Cell(CellCoordinate(subBlockName, column, row), eventBus))
            }
        }
    }

    val cellGroups: Collection<CellGroup> =
            0.rangeTo(9)
                    .map {
                CellGroup.forColumn(it, cells)
            }.union(0.rangeTo(9)
                    .map {
                CellGroup.forRow(it, cells)
            }).union(SubBlockName.values()
                    .map {
                CellGroup.forSubBlock(it, cells)
            })
}

private fun toCellValueChangedEvent(coordinate: CellCoordinate, newValue: Int?): CellValueChangedEvent {
    return CellValueChangedEvent(coordinate, newValue)
}

