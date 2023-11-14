package ui.screen

sealed class Screen(val name: String, private val iconName: String) {
    data object Leaderboard : Screen("Leaderboard", "leaderboard")
    data object Main : Screen("Main", "main")
    data object Settings : Screen("Settings", "settings")
    
    fun iconResource(): String = "icons/$iconName.xml"
}
