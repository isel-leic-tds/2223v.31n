package pt.isel.tds.ttt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.tds.ttt.model.*

/**
 * The Composable function for of player with one label.
 * @param label the label for the player.
 * @param player the player to be presented.
 */
@Composable
fun PlayerView(label: String, player: Player, modifier: Modifier= Modifier) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center
){
    Text("$label: ", fontSize = 20.sp)
    CellView(player, Modifier.size(20.dp))
}

/**
 * The Composable function responsible for the presentation of the status bar.
 */
@Composable
fun StatusBar(board: Board) {
    val mod = Modifier.width(boardSize).background(Color.Yellow).padding(3.dp)
    when(board) {
        is BoardRun -> PlayerView("Turn",board.turn,mod)
        is BoardWin -> PlayerView("Winner",board.winner,mod)
        is BoardDraw -> Text("Draw", modifier = mod, textAlign = TextAlign.Center)
    }
}