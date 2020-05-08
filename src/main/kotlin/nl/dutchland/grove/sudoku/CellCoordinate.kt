package nl.dutchland.grove.sudoku

data class CellCoordinate(val subBlock: SubBlockName, val column: Int, val row: Int) {
    fun hasOverlap(other: CellCoordinate): Boolean {
        return subBlock == other.subBlock ||
                column == other.column ||
                row == other.row
    }
}