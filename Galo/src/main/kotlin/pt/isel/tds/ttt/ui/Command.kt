package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.Game
import pt.isel.tds.ttt.model.InitialGame

/**
 * Base class for all commands.
 * The [execute] method is called to execute the command.
 * The [isToFinish] method is called to check if the command is to finish the game.
 */
abstract class Command {
    open fun execute(args: List<String>, game: Game?) = game
    open fun isToFinish(): Boolean = false
}

object New : Command() {
    override fun execute(args: List<String>, game: Game?) = InitialGame
}

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands() = mapOf(
    "NEW" to New,
    "EXIT" to object : Command() {
        override fun isToFinish() = true
    }
)
