package pt.isel.tds.ttt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import pt.isel.tds.ttt.model.*

/**
 * The principal Composable function of the application.
 * It is responsible for the creation of the application's UI.
 * The content of the application window.
 * @param onExit the function to be called when the application is closed.
 * @param storage the storage to be used by the application.
 */
@Composable
fun FrameWindowScope.GaloApp(onExit: ()->Unit, storage: BoardStorage) {
    val scope = rememberCoroutineScope()
    val vm = remember { GaloViewModel(scope, storage) }       // The ViewModel.
    GaloMenu(vm, onExit)                        // The App menu.
    GaloDialog(vm)                              // The Dialogs.
    // Content of the application window.
    Column {
        BoardView(vm.game?.board, onClick = vm::play)
        StatusBar(vm.status)
    }
}
