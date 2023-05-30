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
 */
@Composable
fun FrameWindowScope.GaloApp(onExit: ()->Unit) {
    val vm = remember { GaloViewModel() }       // The ViewModel.
    GaloMenu(vm, onExit)                        // The App menu.
    GaloDialog(vm)                              // The Dialogs.
    // Content of the application window.
    Column {
        BoardView(vm.game?.board, onClick = vm::play)
        StatusBar(vm.status)
    }
}

@Composable
fun GaloDialog(vm: GaloViewModel) =
    when (val dialog = vm.open) {
        Dialog.NEW -> NameDialog(vm::closeDialog, vm::newGame, "New Game")
        Dialog.JOIN -> NameDialog(vm::closeDialog, vm::joinGame, "Join Game")
        Dialog.HELP -> HelpDialog(vm::closeDialog)
        null -> Unit
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NameDialog(onClose: ()->Unit, onOk: (String)->Unit, title: String) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(title) },
        text = { TextField(name, onValueChange = { name = it }) },
        dismissButton = { Button(onClick = onClose){ Text("Cancel") } },
        confirmButton = { Button(onClick = { onOk(name) }){ Text("Ok") } }
    )
}