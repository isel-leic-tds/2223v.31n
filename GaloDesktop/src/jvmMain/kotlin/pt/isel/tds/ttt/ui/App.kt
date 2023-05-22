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
    val vm = remember { GaloViewModel() }       // The ViewModel.
    GaloMenu(vm, onExit)                        // The App menu.
    if (vm.helpOpen) HelpDialog(vm::closeHelp)  // The Help dialog.

    // Content of the application window.
    Column {
        BoardView(vm.board, onClick = vm::play)
        StatusBar(vm.status)
    }
}
