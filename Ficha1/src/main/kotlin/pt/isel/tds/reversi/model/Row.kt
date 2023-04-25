package pt.isel.tds.reversi.model

/**
 * Represents a row in the board.
 * @property index the row index, starting at 0
 * @property number the row number, starting at 1
 * Primary constructor is private, use the companion object to create instances.
 */
/*@JvmInline value*/ class Row private constructor(val index: Int) {
    val number get() = index+1
    override fun toString() = "Row $number"
    companion object {
        // All valid rows.
        val values = List(BOARD_DIM) { Row(it) }
        /**
         * Creates a row from its [number].
         * Throws [IndexOutOfBoundsException] if the [number] is not valid.
         */
        operator fun invoke(number: Int) = values[number-1]
    }
}

/**
 * Converts a number to a row.
 * @return The row corresponding to the number, or null if the number is not valid.
 */
fun Int.toRowOrNull() = if (this in 1..BOARD_DIM) Row.values[this-1] else null

/**
 * Converts a number to a row.
 * @throws IllegalArgumentException if the number is not valid.
 */
fun Int.toRow() = requireNotNull( toRowOrNull() ) { "Invalid row $this" }

