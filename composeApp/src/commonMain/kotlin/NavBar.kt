import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavBar(page: MutableState<Screen>) {
    val allScreens = listOf(
        Screen.Leaderboard,
        Screen.Main,
        Screen.Settings,
    )

    BottomNavigation {
        allScreens.forEach { screen ->
            BottomNavigationItem(
                selected = page.value == screen,
                onClick = {
                    page.value = screen
                },
                icon = {
                    Icon(
                        painter = painterResource(res = screen.iconResource()),
                        contentDescription = screen.name,
                    )
                },
                label = {
                    Text(screen.name)
                }
            )
        }
    }
}
