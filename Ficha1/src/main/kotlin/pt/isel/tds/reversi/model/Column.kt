package pt.isel.tds.reversi.model

/**
 * Represents a column in the board.
 * @property index The index of the column (0 until BOARD_DIM)
 * @property symbol The symbol of the column ('A' until 'A'+BOARD_DIM)
 * Primary constructor is private, use the companion object to create instances.
 */
/* @JvmInline value*/ class Column private constructor(val index: Int) {
    val symbol get() = 'A'+index
    override fun toString() = "Column $symbol"
    companion object {
        // All valid columns.
        val values = List(BOARD_DIM) { Column(it) }
        /**
         * Creates a column from its [symbol].
         * Throws [IndexOutOfBoundsException] if the [symbol] is not valid.
         */
        operator fun invoke(symbol: Char) = values[ symbol-'A' ]
    }
}

/**
 * Converts a character to a column.
 * @return The column corresponding to the character, or null if the character is not valid.
 */
fun Char.toColumnOrNull() = (this-'A').let{ idx -> Column.values.firstOrNull { it.index==idx } }

/**
 * Converts a character to a column.
 * @return The column corresponding to the character.
 * @throws IllegalArgumentException if the character is not valid.
 */
fun Char.toColumn() = requireNotNull( toColumnOrNull() ) { "Invalid column $this" }
