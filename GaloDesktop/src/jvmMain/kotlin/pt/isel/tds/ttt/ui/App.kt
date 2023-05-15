package pt.isel.tds.ttt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import pt.isel.tds.ttt.model.*

/**
 * The principal Composable function of the application.
 * It is responsible for the creation of the application's UI.
 * The content of the application window.
 * @param onExit the function to be called when the application is closed.
 */
@Composable
fun FrameWindowScope.GaloApp(onExit: ()->Unit) {
    // The state of the application.
    var board by remember{ mutableStateOf<Board>( createBoard(Player.X) ) }
    GaloMenu(
        isNew = board.isNew,
        onExit,
        onNew = { board = createBoard(Player.X) }
    )
    // Content of the application window.
    Column {
        BoardView(board){
            if (board is BoardRun)
                board = board.play(it)
            /*try {
                board = board.play(it)
            } catch (e: Exception) {
                println(e.message)
            }*/
        }
        StatusBar(board)
    }
}
