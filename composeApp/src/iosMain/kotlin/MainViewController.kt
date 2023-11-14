import androidx.compose.ui.window.ComposeUIViewController
import model.createDatabase
import model.DriverFactory

fun MainViewController() = ComposeUIViewController {
    val db = createDatabase(DriverFactory())

    App(db)
}
