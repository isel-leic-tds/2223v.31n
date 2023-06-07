package pt.isel.tds.ttt.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import pt.isel.tds.storage.TextFileStorage
import pt.isel.tds.ttt.model.*
import kotlin.concurrent.thread

/**
 * View model of the application.
 * Manages the state of the game and open dialogs.
 */
class GaloViewModel(
    private val scope: CoroutineScope,      // Scope of composed coroutines
    private val storage: BoardStorage       // Storage to use in game operations
) {
    //region Game State
    // Game being played
    var game by mutableStateOf<Game?>(null)
        private set

    // Creates a new game with the given name and close the dialog
    fun newGame(name: String) = tryRun {
        val g = game
        if (g==null || !g.board.isNew) {
            game = createGame(name, storage)
            closeDialog()
        }
    }

    // Joins the game with the given name and close the dialog
    fun joinGame(name: String) = tryRun{
        scope.launch {
            game = joinGame(name, storage)
            loopAutoRefresh()
            closeDialog()
        }
    }

    // Plays the given position in the game
    fun play(pos: Position) = tryRun {
        game = game?.play(pos, storage)
        loopAutoRefresh()
    }

    // Refreshes the game using a coroutine
    fun refreshGame() = scope.tryLaunch {
        game = game?.refresh(storage)
    }

    // Returns true if the game can be manually refreshed
    val canRefresh: Boolean get() = mayRefresh() && !autoRefresh
    //endregion

    //region Refresh State
    // Returns true if it makes sense to refresh
    private fun mayRefresh(): Boolean =
        game?.run { board is BoardRun && board.turn != player } ?: false

    // Auto refresh state
    var autoRefresh by mutableStateOf(false)
        private set

    // Toggle the auto refresh state and start the loop if needed
    fun toggleAutoRefresh() {
        autoRefresh = !autoRefresh
        if (autoRefresh) loopAutoRefresh()
        else refresh?.let {
            // Cancel the loop
            it.cancel()
            println("+")
            refresh = null
        }
    }

    // Coroutine job of the loop that refreshes the game
    private var refresh: Job? by mutableStateOf(null)

    // Starts the auto refresh loop that refreshes the game every 2 seconds.
    // The loop is stopped when the game was modified.
    private fun loopAutoRefresh() {
        if (autoRefresh && mayRefresh()) {
            refresh = scope.launch {
                while (true) {
                    print("R")
                    game = game?.refresh(storage, checked = false)
                    if (!mayRefresh()) break
                    delay(2000)
                }
                // Normal termination
                println(".")
                refresh = null
            }
        }
    }
    //endregion

    //region Dialogs State
    // The dialog opened
    var open by mutableStateOf<Dialog?>(null)
        private set

    fun openDialog(d: Dialog) { open=d }
    fun closeDialog() { open=null }

    // Message to be displayed in message dialog
    var message = "No message"
        private set

    // Opens a message dialog with the given exception message
    private fun openMessageDialog(e: Exception) {
        message = e.message ?: "Unknown error"
        openDialog(Dialog.MESSAGE)
    }
    //endregion

    //region Status information
    val status: StatusInfo
        get() = when(val b= game?.board) {
            is BoardRun -> StatusInfo("Turn",b.turn,refresh!=null)
            is BoardWin -> StatusInfo("Winner",b.winner)
            is BoardDraw -> StatusInfo("Draw")
            null -> StatusInfo("No Game")
        }
    //endregion

    //region Auxiliary Functions
    // Executes a block and open a message dialog if an illegal state exception is thrown.
    private inline fun tryRun(block: ()->Unit) =
        try { block() } catch (e: IllegalStateException) { openMessageDialog(e) }

    // Launch a coroutine to execute a block and open a message dialog if an illegal state exception is thrown.
    // NOTE: The block is executed is a suspend function.
    private fun CoroutineScope.tryLaunch(block: suspend()->Unit) =
        launch {
            try { block() } catch (e: IllegalStateException) {
                openMessageDialog(e)
            }
        }
    //endregion
}

// Dialogs of the application
enum class Dialog  { NEW, JOIN, HELP, MESSAGE }

// Status of the game to be displayed in status bar
data class StatusInfo(val label: String, val player: Player?=null, val refreshing: Boolean = false)


