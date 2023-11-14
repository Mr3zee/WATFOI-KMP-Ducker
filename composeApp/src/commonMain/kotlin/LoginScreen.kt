import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import storage.Database
import storage.LoggedUser

@Composable
fun LoginScreen(userState: MutableState<LoggedUser?>, database: Database) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var error by remember { mutableStateOf<String?>(null) }

            Text("Login or register", fontSize = 35.sp, fontWeight = FontWeight.Bold)

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                isError = error?.startsWith("Username") ?: false
            )

            Box(Modifier.height(4.dp))

            TextField(
                value = "*".repeat(password.length),
                onValueChange = {
                    password = it
                },
                label = { Text("Password") },
                isError = error?.startsWith("Password") ?: false
            )
            
            val errorText = error
            if (errorText != null) {
                Text(errorText, color = MaterialTheme.colors.error)
            }

            Button(onClick = {
                when {
                    username.isBlank() -> {
                        error = "Username can not be empty"
                    }

                    password.isBlank() -> {
                        error = "Password can not be empty"
                    }

                    else -> {
                        error = null
                        // todo server validation

                        database.userQueries.login(username, password)
                        userState.value = LoggedUser(username, password)
                    }
                }
            }) {
                Text("Login/Register")
            }
        }
    }
}
