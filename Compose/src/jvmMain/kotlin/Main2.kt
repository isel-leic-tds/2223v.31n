import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay

@Composable
@Preview
fun FrameWindowScope.Counter(onExit: ()->Unit ) {
    var counter by remember { mutableStateOf(0) }
    CounterMenu(onExit, onReset = { counter=0 })
    Column(
        modifier = Modifier.fillMaxWidth().padding(5.dp).background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Content(counter) { counter += it }
        LaunchedEffect(counter){
            println("Launch $counter")
            while (true) {
                delay(1000)
                counter++
            }
        }
    }
}

@Composable
fun Content(counter: Int, onChange: (delta: Int)->Unit ) {
    println("Content")
    Text("Counter = $counter", fontSize = 32.sp)
    repeat(5) {
        print("$it ")
        IncDecButtons(it + 1, changeValue = onChange)
    }
}

@Composable
fun FrameWindowScope.CounterMenu(onExit: () -> Unit, onReset: ()->Unit) {
    MenuBar {
        Menu("Menu") {
            Item("Exit", onClick = onExit)
            Item("Reset", onClick = onReset)
        }
    }
}

@Composable
fun IncDecButtons(delta: Int, changeValue: (Int)->Unit ) {
    Row(modifier = Modifier.fillMaxWidth().background(Color.Green),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { changeValue(+delta) }) {
            Text("+$delta")
        }
        Button(onClick = { changeValue(-delta)}) {
            Text("-$delta")
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
            Counter(::exitApplication)
        }
    }
    println("End.")
}
