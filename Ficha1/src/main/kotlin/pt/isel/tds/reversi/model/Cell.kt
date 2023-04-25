package pt.isel.tds.reversi.model

/**
 * Represents a row in the board with [row] and [col]
 * @property index the index in board, starting at 0 (topLeft to bottomRight)
 * @property rowIndex the row index, starting at 0 (top to bottom)
 * @property colIndex the column index, starting at 0 (left to right)
 * @property row the row value @see [Row]
 * @property col the column value @see [Column]
 * Primary constructor is private, use the companion object to create instances.
 */
/*@JvmInline value*/ class Cell private constructor(private val index: Int) {
    val rowIndex get() = index/BOARD_DIM // same value as row.index
    val colIndex get() = index%BOARD_DIM // same value as col.index
    val row get() = Row.values[rowIndex]
    val col get() = Column.values[colIndex]
    override fun toString() = if (this==INVALID) "INVALID Cell" else "${row.number}${col.symbol}"
    companion object {
        // All valid cells.
        val values = List(BOARD_DIM*BOARD_DIM) { Cell(it) }
        // The invalid cell.
        val INVALID = Cell(-1)
        /**
         * Creates a cell from its [row] and [col].
         * Always succeeds because the [row] and [col] are validated.
         */
        operator fun invoke(row: Row, col: Column) = values[row.index*BOARD_DIM + col.index]
        /**
         * Creates a cell from its [rowIndex] and [colIndex].
         * returns [INVALID] if the [rowIndex] or [colIndex] are not valid.
         */
        operator fun invoke(indexRow: Int, indexCol: Int) =
            if (indexRow in 0 until BOARD_DIM && indexCol in 0 until BOARD_DIM)
                values[indexRow* BOARD_DIM + indexCol]
            else INVALID
    }
}

/**
 * Converts a string in format <Row Number><Column Symbol> to a cell.
 * @return The cell corresponding to the string.
 * @throws IllegalArgumentException if the string is not valid.
 */
fun String.toCell(): Cell {
    require(length==2) { "Cell must have row and column" }
    return Cell(this[0].digitToInt().toRow(), this[1].toColumn())
}

/**
 * Converts a string in format <Row Number><Column Symbol> to a cell.
 * @return The cell corresponding to the string, or null if the string is not valid.
 */
fun String.toCellOrNull(): Cell? =
    if (length!=2) null
    else this[0].digitToInt().toRowOrNull()?.let { row ->
        this[1].toColumnOrNull()?.let { col -> Cell(row, col) }
    }

/**
 * Direction of possible lines formed by the cells.
 * @property difRow the difference in row index for the direction
 * @property difCol the difference in column index for the direction
 */
enum class Direction(val difRow: Int, val difCol: Int) {
    UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1),
    UP_LEFT(-1,-1), UP_RIGHT(-1,1), DOWN_LEFT(1,-1), DOWN_RIGHT(1,1)
}

/**
 * Adds a direction to a cell resulting in a new cell.
 * @return The cell resulting or [Cell.INVALID] if the cell is out of the board.
 */
operator fun Cell.plus(dir: Direction) = Cell(row.index+dir.difRow, col.index+dir.difCol)

/**
 * Returns the cells of the board in a line starting at [from] (excluding) in the direction [dir].
 * @param from the cell where the line starts (exclusive)
 * @param dir the direction of the line starting at [from]
 * @return The list of cells in the line.
 */
fun cellsInDirection(from: Cell, dir: Direction) = buildList {
    var cell = from
    while ((cell + dir).also { cell = it } != Cell.INVALID) add(cell)

    // ALTERNATIVE IMPLEMENTATIONS
    /* 1 - With not tail recursive implementation
    = (from+dir).let { if (it==Cell.INVALID) emptyList() else listOf(it) + cellsInDirection(it,dir)}
     */
    /* 2 - With local tail recursive function
    tailrec fun cid(before: List<Cell>, next: Cell): List<Cell> =
        if (next==Cell.INVALID) before else cid(before+next, next+dir)
    return cid(emptyList(), from+dir)
    */
    /* 3 - With a Sequence
    = generateSequence(from+dir) { (it+dir).takeIf { it!=Cell.INVALID } }.toList()
    */
}