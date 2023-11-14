import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import api.apiService
import model.Model
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.load
import storage.Database
import ui.AppBody
import ui.NavBar
import ui.screen.LoginScreen
import ui.screen.Screen

@Composable
fun App(database: Database) {
    MaterialTheme {
        TryLogIn(database) { initialSettings ->
            val userSettings = remember { mutableStateOf(initialSettings) }

            when (userSettings.value) {
                null -> {
                    LoginScreen(userSettings, database)
                }

                else -> {
                    val screen: MutableState<Screen> = remember { mutableStateOf(Screen.Main) }
                    val model: Model = Model.create(userSettings, database)

                    Scaffold(
                        bottomBar = {
                            NavBar(screen)
                        }
                    ) {
                        AppBody(screen.value, model)
                    }
                }
            }
        }
    }
}

@Composable
private fun TryLogIn(database: Database, content: @Composable (UserSettings?) -> Unit) {
    val userState = load {
        val user = database.userQueries.getUser().executeAsOneOrNull() ?: return@load null
        apiService.loginOrRegister(User(user.name, user.password))
    }

    when (userState) {
        is LoadState.Error -> content(null)

        is LoadState.Success -> {
            val value = userState.value
            if (value == null) {
                content(null)
            } else {
                content(userState.value)
            }
        }

        else -> {}
    }
}
