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

private val CellSize = 30.dp
private val FontSize = 20.sp

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
    Text("$label: ", fontSize = FontSize)
    CellView(player, Modifier.size(CellSize))
}

/**
 * The Composable function responsible for the presentation of the status bar.
 */
@Composable
fun StatusBar(info: StatusInfo) {
    val mod = Modifier.width(boardSize).height(CellSize).background(Color.Yellow).padding(3.dp)
    val (label,player) = info
    if (player==null)
        Text(label, modifier = mod, fontSize = FontSize, textAlign = TextAlign.Center)
    else
        PlayerView(label,player,mod)
}