package pt.isel.tds.ttt

fun main() {
    var game: Game? = null
    val commands = getCommands()
    while (true) {
        val (name, args) = readCommand()
        val cmd = commands[name]
        if (cmd==null) println("Invalid command $name")
        else {
            game = cmd.execute(args, game)
            if (cmd.isToFinish()) break
            game?.show()
        }
    }
    println("Bye.")
}

fun Game.show() { }

fun getCommands() = mapOf<String,Command>(
    "EXIT" to object : Command() {
        override fun isToFinish() = true
    }
)

abstract class Command {
    open fun execute(args: List<String>, game: Game?) = game
    abstract fun isToFinish(): Boolean
}

class Game()

data class CommandLine(val name: String, val args: List<String>)

tailrec fun readCommand(): CommandLine {
    print("> ")
    val line = readln().split(' ').filter { it.isNotBlank() }
    return if (line.isEmpty()) readCommand()
    else CommandLine(line.first().uppercase(), line.drop(1))
}