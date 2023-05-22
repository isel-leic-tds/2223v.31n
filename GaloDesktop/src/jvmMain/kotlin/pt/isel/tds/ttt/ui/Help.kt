package pt.isel.tds.ttt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition

@Composable
fun HelpDialog2(onClose: ()->Unit) = Dialog(
    onCloseRequest = onClose,
    state = DialogState(size= DpSize.Unspecified, position = WindowPosition.PlatformDefault),
    title = "Galo Help"
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Jogo do Galo")
        Text("ISEL", fontSize = 20.sp)
        Text("Técnicas de Desenvolvimento de Software")
        Button(onClick = onClose) {
            Text("OK")
        }
    }
}

/**
 * Help dialog.
 * @param onClose the function to be called when the dialog is closed.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HelpDialog(onClose: ()->Unit) = AlertDialog(
    onDismissRequest = onClose,
    confirmButton = { Button(onClick = onClose){ Text("Ok") } },
    text = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width( boardSize * 0.8f )
        ) {
            Text("Jogo do Galo")
            Text("ISEL", fontSize = 20.sp)
            Text("Técnicas de Desenvolvimento de Software")
        }
    }
)