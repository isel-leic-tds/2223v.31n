package pt.isel.tds.ttt.model

import pt.isel.tds.storage.Storage

typealias BoardStorage = Storage<String, Board>
//interface BoardStorage: Storage<String, Board>

/**
 * Represents each side of the game.
 */
data class Game(
    val board: Board,
    val player: Player,
    val id: String
)

/**
 * Creates a new game.
 */
fun createGame(id: String, st: BoardStorage) =
    Game(createBoard(Player.X), Player.X, id).also {
        st.create(it.id, it.board)
    }

/**
 * Joins an existing game.
 */
suspend fun joinGame(id: String, st: BoardStorage): Game {
    val board = checkNotNull( st.read(id) ) { "Game not found" }
    check( board is BoardRun && board.moves.size <= 1 ) { "Game is not available" }
    return Game(board, Player.O, id)
}

/**
 * Makes a move in the game.
 */
fun Game.play(pos: Position, st: BoardStorage): Game {
    if (board is BoardRun)
        check(player == board.turn ) { "Is not your turn" }
    return copy( board= board.play(pos) ).also { st.update(id, it.board) }
}

/**
 * Refreshes the game.
 *
 */
suspend fun Game.refresh(st: BoardStorage, checked: Boolean = true): Game {
    val board = st.read(id) ?: throw Exception("Game not found")
    if (checked) check( board != this.board ) { "No changes" }
    return if (board != this.board ) copy( board = board ) else this
}
