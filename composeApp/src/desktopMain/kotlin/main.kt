import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.createDatabase
import model.DriverFactory

fun main() = application {
    val db = createDatabase(DriverFactory())
    
    Window(onCloseRequest = ::exitApplication) {
        App(db)
    }
}
