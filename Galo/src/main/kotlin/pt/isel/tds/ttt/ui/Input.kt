package pt.isel.tds.ttt.ui

/**
 * Represents a command line with a name and a list of arguments.
 * Assumes that the name is in uppercase.
 */
data class CommandLine(val name: String, val args: List<String>)

/**
 * Reads a command line from the standard input.
 * @return the command line read.
 */
tailrec fun readCommand(): CommandLine {
    print("> ")
    val line = readln().split(' ').filter { it.isNotBlank() }
    return if (line.isEmpty()) readCommand()
    else CommandLine(line.first().uppercase(), line.drop(1))
}