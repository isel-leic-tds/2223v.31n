package pt.isel.tds.ttt.model

/**
 * Represents a position in the board.
 * The [index] is the position in the board, (0 until [MAX_MOVES]).
 * The [row] and [col] are computed from the [index].
 * For efficiency, the instances are created in advance and stored in [values].
 * The builder function [Position] simulate the constructor.
 * For efficiency, this type is a value class, that is represented as an [Int].
 */
@JvmInline
value class Position private constructor(val index: Int) {
    val row: Int get() = index / BOARD_SIZE
    val col: Int get() = index % BOARD_SIZE
    val backSlash get() = row == col            // Is in principal diagonal?
    val slash get() = row+col == BOARD_SIZE-1   // Is in secondary diagonal?

    override fun toString() = "$index"
    companion object {
        val values = List(MAX_MOVES) { Position(it) }
        /**
         * Creates a [Position] from the given [index].
         * @throws IllegalArgumentException if the [index] is out of range.
         */
        operator fun invoke(index: Int): Position {
            require(index in 0 until MAX_MOVES)
            return values[index]
        }
    }
}

/**
 * Creates a [Position] from the given [row] and [col].
 * @throws IllegalArgumentException if the [row] or [col] are out of range.
 */
fun Position(row: Int, col: Int) = run {
    require(row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE)
    Position.values[row * BOARD_SIZE + col]
}

/**
 * Creates a [Position] from the given [index] or returns null if the [index] is out of range.
 */
fun Int.toPositionOrNull() =
    if (this in Position.values.indices) Position(this) else null