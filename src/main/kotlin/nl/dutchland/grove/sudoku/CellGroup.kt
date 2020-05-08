package nl.dutchland.grove.sudoku

class CellGroup private constructor(private val cells: Collection<Cell>){
    fun check() {
        0.rangeTo(9).forEach { number ->
            val cellsWithThatPossibility = cells.filter { it.possibleValues.contains(number) }

            if (cellsWithThatPossibility.size == 1) {
                cellsWithThatPossibility[0].value = number
            }
        }
    }

    companion object {
        fun forRow(row: Int, cells: Collection<Cell>) : CellGroup {
            return CellGroup(cells.filter { cell -> cell.coordinate.row == row })
        }

        fun forColumn(column: Int, cells: Collection<Cell>) : CellGroup {
            return CellGroup(cells.filter { cell -> cell.coordinate.column == column })
        }

        fun forSubBlock(subBlockName: SubBlockName, cells: Collection<Cell>) : CellGroup {
            return CellGroup(cells.filter { cell -> cell.coordinate.subBlock == subBlockName })
        }
    }
}