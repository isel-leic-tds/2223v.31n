package pt.isel.tds.ttt

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import pt.isel.tds.ttt.ui.GaloApp

fun main() = application(exitProcessOnExit = false) {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Galo TDS",
        state = WindowState(size = DpSize.Unspecified)
    ) {
        GaloApp(::exitApplication)
    }
}
