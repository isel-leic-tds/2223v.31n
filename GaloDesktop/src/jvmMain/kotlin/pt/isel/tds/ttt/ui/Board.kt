package pt.isel.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pt.isel.tds.ttt.model.*

// Dimensions of the board presentation.
val cellSize = 150.dp
val lineSize = 10.dp
val boardSize = cellSize * BOARD_SIZE + lineSize*(BOARD_SIZE -1)

@Composable
@Preview
fun BoardViewTest() =
    BoardView( createBoard(Player.X).play(Position(4)).play(Position(0))) {}

/**
 * The Composable function responsible for the presentation of the board.
 * @param board the board to be presented.
 * @param onClick the function to be called when a cell is clicked.
 */
@Composable
fun BoardView(board: Board, onClick: (Position) -> Unit) {
    Column(
        modifier = Modifier.size(boardSize).background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(BOARD_SIZE) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(BOARD_SIZE) { col->
                    val pos = Position(row,col)
                    CellView(board.moves[pos]) { onClick(pos) }
                }
            }
        }
    }
}

@Composable
@Preview
fun CelViewTest() = CellView(Player.X) {}

/**
 * The Composable function responsible for the presentation of each cell.
 * @param player the player that played on the cell.
 * @param modifier the modifier to be applied to the cell.
 * @param onClick the function to be called when the cell is clicked.
 */
@Composable
fun CellView(
    player: Player?,
    modifier: Modifier = Modifier.size(cellSize).background(Color.LightGray),
    onClick: () -> Unit = {},
) {
    if (player==null)
        Box(modifier = modifier.clickable(onClick = onClick))
    else
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ){
            var fill by remember(player) { mutableStateOf(0.1f) }
            val image = when (player) {
                Player.O -> "circle.png"
                Player.X -> "cross.png"
            }
            Image(painterResource(image), modifier =Modifier.fillMaxSize(fill), contentDescription = image)
            LaunchedEffect(player) {
                while(fill<1f) {
                    delay(50)
                    fill *= 2
                }
                fill = 1f
            }
        }
}
