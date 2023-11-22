package ui

import model.Model
import androidx.compose.runtime.Composable
import ui.screen.Leaderboard
import ui.screen.MainScreen
import ui.screen.Screen
import ui.screen.SettingsScreen

@Composable
fun AppBody(screen: Screen, model: Model) {
    when (screen) {
        Screen.Main -> {
            MainScreen(model)
        }
        Screen.Leaderboard -> {
            Leaderboard(model)
        }
        Screen.Settings -> {
            SettingsScreen(model)
        }
    }
}
