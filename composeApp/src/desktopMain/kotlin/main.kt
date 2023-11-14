import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val db = createDatabase(DriverFactory())
    
    Window(onCloseRequest = ::exitApplication) {
        App(db)
    }
}
