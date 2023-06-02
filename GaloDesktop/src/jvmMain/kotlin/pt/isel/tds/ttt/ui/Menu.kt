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
        Item("New", onClick = { vm.openDialog(Dialog.NEW) })
        Item("Join", onClick = { vm.openDialog(Dialog.JOIN) })
        Item("Refresh", enabled = vm.canRefresh, onClick = vm::refreshGame)
        Item("Exit", onClick = onExit)
    }
    Menu("Help") {
        Item("About", onClick = { vm.openDialog(Dialog.HELP) })
    }
    Menu("Options") {
        CheckboxItem("Auto Refresh",
            checked = vm.autoRefresh,
            onCheckedChange = { vm.toggleAutoRefresh() }
        )
    }
}