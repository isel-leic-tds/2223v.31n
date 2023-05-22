package pt.isel.tds.ttt.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pt.isel.tds.ttt.model.*

class GaloViewModel {
    var board by mutableStateOf<Board>( createBoard(Player.X) )
    private set
    fun newGame() {
        if (!board.isNew)
            board = createBoard(Player.X)
    }
    fun play(pos: Position) {
        if (board is BoardRun)
            board = board.play(pos)
    }

    var helpOpen by mutableStateOf(false)
    private set
    fun openHelp() { helpOpen=true }
    fun closeHelp() { helpOpen=false }

    val status: StatusInfo
        get() = when(val b= board) {
            is BoardRun -> "Turn" of b.turn
            is BoardWin -> "Winner" of b.winner
            is BoardDraw -> "Draw" of null
        }
}

data class StatusInfo(val label: String, val player: Player?)
infix fun String.of(p: Player?) = StatusInfo(this,p)