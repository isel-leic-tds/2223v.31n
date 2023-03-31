package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.*

/**
 * Base class for all commands.
 * The [execute] method is called to execute the command.
 * The [isToFinish] method is called to check if the command is to finish the game.
 */
abstract class Command(val argsSyntax: String = ""){
    open fun execute(args: List<String>, game: Game?) = game
    open fun isToFinish(): Boolean = false
}

/**
 * Command to start a new game.
 * The first player is X for the first game and alternated between games.
 */
object New : Command() {
    override fun execute(args: List<String>, game: Game?) = run {
        val player = game?.firstPlayer?.other() ?: Player.X
        Game( createBoard(player), player)
    }
}

/**
 * Command to play a move in the game.
 * The position is given as a single integer in the range 0..BOARD_SIZE*BOARD_SIZE-1.
 */
object Play: Command("<position>") {
    override fun execute(args: List<String>, game: Game?): Game {
        require(args.isNotEmpty()) { "Missing position" }
        val pos = requireNotNull(args.first().toIntOrNull()?.toPositionOrNull())
            {"Invalid position ${args.first()}"}
        checkNotNull(game) { "Game not started" }
        return game.play(pos)
    }
}

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands() = mapOf(
    "PLAY" to Play,
    "NEW" to New,
    "EXIT" to object : Command() {
        override fun isToFinish() = true
    }
)
