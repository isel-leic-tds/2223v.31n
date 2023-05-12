package pt.isel.tds.ttt.model

/**
 * Represents each player of the game.
 */
enum class Player { X, O;
    fun other() = if (this==X) O else X
}

const val BOARD_SIZE = 3   // The size of the board in (2..N)
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE

// Type to represents all the moves in the game.
typealias Moves = Map<Position,Player>

/**
 * Represents the board of the game.
 * Store all [moves] in a map from [Position] to [Player] ([Moves]).
 * There are three possible states of board: [BoardRun], [BoardWin] and [BoardDraw].
 * These hierarchy is to be used by pattern matching.
 */
sealed class Board(val moves: Moves) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false
        if (this::class != other::class) return false
        return moves.size == other.moves.size
    }
    override fun hashCode(): Int = moves.hashCode()
}
class BoardRun(moves: Moves, val turn: Player): Board(moves)
class BoardWin(moves: Moves, val winner: Player): Board(moves)
class BoardDraw(moves: Moves) : Board(moves)

/**
 * Creates a new board with the given [first] as the first turn.
 */
fun createBoard(first: Player) = BoardRun(emptyMap(), first)

/**
 * Makes a move in [pos] position by the current turn.
 * @throws IllegalArgumentException if the [pos] is already used.
 * @throws IllegalStateException if the game is over (Draw or Win).
 */
fun Board.play(pos: Position): Board = when(this) {
    is BoardRun -> {
        require(moves[pos] == null) { "Position $pos used" }
        val moves = moves + (pos to turn)
        when {
            isWin(pos) -> BoardWin(moves, turn)
            moves.size == MAX_MOVES -> BoardDraw(moves)
            else -> BoardRun(moves, turn.other())
        }
    }
    is BoardWin, is BoardDraw -> error("Game over")
}

/**
 * Checks if the move in [pos] position is a winning move.
 */
private fun BoardRun.isWin(pos: Position) =
    moves.size >= BOARD_SIZE*2-2 &&
    (moves.filter { it.value == turn }.keys + pos).run {
        count{ it.row == pos.row } == BOARD_SIZE ||
        count{ it.col == pos.col } == BOARD_SIZE ||
        count{ it.slash } == BOARD_SIZE || count{ it.backSlash } == BOARD_SIZE
    }
