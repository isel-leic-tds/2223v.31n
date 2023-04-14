package pt.isel.tds.ttt.ui

import pt.isel.tds.storage.Storage
import pt.isel.tds.ttt.model.*

/**
 * Represents a command supported by the application.
 * @param argsSyntax the syntax of the command arguments.
 * @param isToFinish a function that returns true if the command is to finish the application.
 * @param execute a function that executes the command.
 */
data class Command(
    val argsSyntax: String = "",
    val isToFinish: () -> Boolean = { false },
    val execute: (List<String>, Game?) -> Game? = { _, g -> g }
)

typealias BoardStorage = Storage<String, Board>
/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands(st: BoardStorage) = mapOf(
    "PLAY" to Command("<position>") { args, game ->
        require(args.isNotEmpty()) { "Missing position" }
        val pos = requireNotNull(args.first().toIntOrNull()?.toPositionOrNull())
            {"Invalid position ${args.first()}"}
        checkNotNull(game) { "Game not started" }
        game.play(pos, st)
    },
    "NEW" to Command("<game>"){ args, _ ->
        require(args.isNotEmpty()) { "Missing game" }
        createGame(args[0], st)
    },
    "JOIN" to Command("<game>"){ args, _ ->
        require(args.isNotEmpty()) { "Missing game" }
        joinGame(args[0], st)
    },
    "REFRESH" to Command { _, game ->
        checkNotNull(game) { "Game not started" }
        game.refresh(st)
    },
    "EXIT" to Command(isToFinish = { true })
)

