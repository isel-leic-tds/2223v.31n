import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

@Composable
@Preview
fun Counter() {
    var counter by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp).background(Color.LightGray)) {
        Text("Counter = $counter", fontSize = 32.sp)
        incDecButtons{
            counter += it
        }
    }
}

@Composable
fun incDecButtons( changeValue: (Int)->Unit ) {
    Row(modifier = Modifier.fillMaxWidth(0.9f).background(Color.Green), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { changeValue(+1) }) {
            Text("Increment")
        }
        Button(onClick = { changeValue(-1)}) {
            Text("Decrement")
        }
    }
}

fun main() {
    println("Start")
    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(height = Dp.Unspecified, width = 300.dp)
        ) {
            Counter()
        }
    }
    println("End.")
}
