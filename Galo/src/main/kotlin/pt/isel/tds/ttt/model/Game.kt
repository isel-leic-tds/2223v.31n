package pt.isel.tds.ttt.model

/**
 * Represents each side of the game.
 * TODO: This is a preliminary version.
 */
data class Game(
    val board: Board,
    val firstPlayer: Player,
//    val player: Player,
//    val id: String
)

/**
 * Makes a move in the game.
 * TODO: This is a preliminary version.
 */
fun Game.play(pos: Position) = copy( board= board.play(pos) )