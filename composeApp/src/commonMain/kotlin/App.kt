import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun App() {
    val model: Model = remember { Model(User("me")) }
    val screen: MutableState<Screen> = remember { mutableStateOf(Screen.Main) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavBar(screen)
            }
        ) {
            AppBody(screen.value, model)
        }
    }
}
