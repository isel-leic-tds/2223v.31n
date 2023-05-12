package pt.isel.tds.ttt.model

import pt.isel.tds.storage.Serializer

/**
 * Serializer of a board to a string.
 * The string is composed of two lines:
 * - the first line is the kind of the board and the player (turn or winner)
 * - the second line is a list of moves, each move is a pair of position and player
 */
object BoardSerializer : Serializer<Board,String> {
    override fun serialize(data: Board) = buildString {
        appendLine( when(data) {
            is BoardRun -> "Run:${data.turn}"
            is BoardWin -> "Win:${data.winner}"
            is BoardDraw -> "Draw:-"
        })
        appendLine(data.moves.entries.joinToString(" ") {
            "${it.key.index}:${it.value.name}"
        })
    }
    override fun deserialize(stream: String): Board {
        val (header, movesLine) = stream.split("\n")
        val (kind,player) = header.split(":")
        val moves =
            if (movesLine.isEmpty()) emptyMap()
            else movesLine.split(" ").associate {
                val (k,v) = it.split(":")
                k.toInt().toPosition() to Player.valueOf(v)
            }
        return when(kind) {
            "Run" -> BoardRun(moves, Player.valueOf(player))
            "Win" -> BoardWin(moves, Player.valueOf(player))
            "Draw" -> BoardDraw(moves)
            else -> error("Invalid board kind: $kind")
        }
    }
}