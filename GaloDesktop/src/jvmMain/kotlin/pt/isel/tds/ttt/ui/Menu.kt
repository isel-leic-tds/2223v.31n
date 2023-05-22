package pt.isel.tds.ttt.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import pt.isel.tds.ttt.model.*

/**
 * Menu of the application.
 */
@Composable
fun FrameWindowScope.GaloMenu(vm: GaloViewModel, onExit: ()->Unit, ) = MenuBar {
    Menu("Game") {
        Item("New", enabled = !vm.board.isNew, onClick = vm::newGame)
        Item("Exit", onClick = onExit)
    }
    Menu("Help") {
        Item("About", onClick = vm::openHelp)
    }
}