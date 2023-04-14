package pt.isel.tds.ttt

import pt.isel.tds.storage.TextFileStorage
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.*
import pt.isel.tds.ttt.ui.getCommands

/**
 * Main loop for the Tic-Tac-Toe game console application.
 * It reads commands from the console and executes them.
 * The only mutable variable is the game state than is updated by the commands.
 */
fun main() {
    var game: Game? = null
    val commands = getCommands(TextFileStorage("games", BoardSerializer))
    while (true) {
        val (name, args) = readCommand()
        val cmd = commands[name]
        if (cmd==null) println("Invalid command $name")
        else try {
            game = cmd.execute(args, game)
            if (cmd.isToFinish()) break
            game?.show()
        } catch (e: IllegalArgumentException) {
            println("${e.message}\nUse: $name ${cmd.argsSyntax}")
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }
    println("Bye.")
}

