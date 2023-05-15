package pt.isel.tds.ttt.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

/**
 * Menu of the application.
 */
@Composable
fun FrameWindowScope.GaloMenu(isNew: Boolean, onExit: ()->Unit, onNew: ()->Unit) = MenuBar {
    Menu("Game") {
        Item("New", enabled = !isNew, onClick = onNew)
        Item("Exit", onClick = onExit)
    }
    Menu("Help") {
        Item("About") {
            println("Help About")
            //TODO: show about dialog.
        }
    }
}