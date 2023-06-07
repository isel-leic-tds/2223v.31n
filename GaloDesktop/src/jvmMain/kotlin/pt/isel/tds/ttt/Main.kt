package pt.isel.tds.ttt

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import pt.isel.tds.storage.*
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.GaloApp

fun main() {
    MongoDriver().use { driver ->
        val storage: BoardStorage = MongoStorage("games", driver, BoardSerializer)
        //val storage: BoardStorage = TextFileStorage("games",BoardSerializer)
        application(exitProcessOnExit = false) {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Galo TDS",
                state = WindowState(size = DpSize.Unspecified)
            ) {
                GaloApp(::exitApplication,storage)
            }
        }
    }
}
