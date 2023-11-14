import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AppBody(screen: Screen, model: Model) {
    when (screen) {
        Screen.Main -> {
            MainScreen(model)
        }
        Screen.Leaderboard -> {
            Text("Leaderboard")
        }
        Screen.Settings -> {
            SettignsScreen(model)
        }
    }
}
