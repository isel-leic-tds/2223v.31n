package pt.isel.tds.ttt.model

/**
 * Represents each player of the game.
 */
enum class Player { X, O }

const val BOARD_SIZE = 3   // The size of the board in (2..N)
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE

/**
 * Represents the board of the game.
 * Store all [moves] in a map from [Position] to [Player].
 * The [turn] is the player that should play next.
 * TODO: This is a preliminary version.
 */
class Board(val moves: Map<Position,Player>, val turn: Player)

val initialBoard = Board(emptyMap(), Player.X)
