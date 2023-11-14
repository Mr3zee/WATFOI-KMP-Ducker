import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    val db = createDatabase(DriverFactory())

    App(db)
}
