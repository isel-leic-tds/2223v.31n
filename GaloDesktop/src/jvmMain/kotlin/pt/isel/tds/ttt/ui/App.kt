package pt.isel.tds.ttt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import pt.isel.tds.ttt.model.*

/**
 * The principal Composable function of the application.
 * It is responsible for the creation of the application's UI.
 * The content of the application window.
 * @param onExit the function to be called when the application is closed.
 */
@Composable
fun GaloApp(onExit: ()->Unit) {
    // The state of the application.
    var board by remember{ mutableStateOf<Board>( createBoard(Player.X) ) }

    // Content of the application window.
    Column {
        BoardView(board){
            board = board.play(it)
        }
        //TODO StatusBar()
    }
}
