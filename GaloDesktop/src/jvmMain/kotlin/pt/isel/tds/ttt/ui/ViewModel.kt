package pt.isel.tds.ttt.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pt.isel.tds.storage.TextFileStorage
import pt.isel.tds.ttt.model.*

/**
 * View model of the application.
 * Manages the state of the game and open dialogs.
 */
class GaloViewModel {
    // Storage to use in game operations
    private val storage: BoardStorage = TextFileStorage("games",BoardSerializer)

    // Game being played
    var game by mutableStateOf<Game?>(null)
    private set

    // Board of the game being played
    // TODO: remove this property (no longer used)
    val board get() = checkNotNull(game).board

    // Creates a new game with the given name and close the dialog
    fun newGame(name: String) {
        val g = game
        if (g==null || !g.board.isNew) {
            game = createGame(name, storage)
            closeDialog()
        }
    }
    // Joins the game with the given name and close the dialog
    fun joinGame(name: String) {
        game = joinGame(name, storage)
        closeDialog()
    }
    // Plays the given position in the game
    fun play(pos: Position) {
        game = game?.play(pos, storage)
    }

    // The dialog opened
    var open by mutableStateOf<Dialog?>(null)
    private set

    fun openDialog(d: Dialog) { open=d }
    fun closeDialog() { open=null }

    // The status of the game to be displayed in status bar
    val status: StatusInfo
        get() = when(val b= game?.board) {
            is BoardRun -> "Turn" of b.turn
            is BoardWin -> "Winner" of b.winner
            is BoardDraw -> "Draw" of null
            null -> "No Game" of null
        }
    infix fun String.of(p: Player?) = StatusInfo(this,p)
}

// Dialogs of the application
enum class Dialog  { NEW, JOIN, HELP }

// Status of the game to be displayed in status bar
data class StatusInfo(val label: String, val player: Player?)
