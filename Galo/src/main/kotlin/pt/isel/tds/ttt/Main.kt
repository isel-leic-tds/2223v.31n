package pt.isel.tds.ttt

import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.*

/**
 * Main loop for the Tic-Tac-Toe game console application.
 * It reads commands from the console and executes them.
 * The only mutable variable is the game state than is updated by the commands.
 */
fun main() {
    var game: Game? = null
    val commands = getCommands()
    while (true) {
        val (name, args) = readCommand()
        val cmd = commands[name]
        if (cmd==null) println("Invalid command $name")
        else try {
            game = cmd.execute(args, game)
            if (cmd.isToFinish()) break
            game?.show()
        } catch (e: Exception) {
            println(e.message)
            if (e is IllegalArgumentException)
                println("Use: $name ${cmd.argsSyntax}")
        }
    }
    println("Bye.")
}

