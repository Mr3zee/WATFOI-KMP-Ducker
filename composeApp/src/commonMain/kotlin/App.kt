import androidx.compose.material.*
import androidx.compose.runtime.*
import model.Model
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.load
import storage.Database
import storage.LoggedUser
import ui.AppBody
import ui.NavBar
import ui.screen.LoginScreen
import ui.screen.Screen

@Composable
fun App(database: Database) {
    MaterialTheme {
        WithLoggedIn(database) { initialUser ->
            val user = remember { mutableStateOf(initialUser) }

            when (user.value) {
                null -> {
                    LoginScreen(user, database)
                }

                else -> {
                    val screen: MutableState<Screen> = remember { mutableStateOf(Screen.Main) }
                    val model: Model = remember { Model(user, database) }

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
private fun WithLoggedIn(database: Database, content: @Composable (LoggedUser?) -> Unit) {
    val userState = load { database.userQueries.getUser().executeAsOneOrNull() }

    when (userState) {
        is LoadState.Error -> content(null)
        is LoadState.Success -> content(userState.value)
        else -> {}
    }
}
