package ui.screen

import User
import UserSettings
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.apiService
import kotlinx.coroutines.launch
import storage.Database

@Composable
fun LoginScreen(userState: MutableState<UserSettings?>, database: Database) {
    val scope = rememberCoroutineScope()

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
            var inProgress by remember { mutableStateOf(false) }

            Text("Login or register", fontSize = 35.sp, fontWeight = FontWeight.Bold)

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                isError = error?.startsWith("Username") ?: false
            )

            Box(Modifier.height(4.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password") },
                isError = error?.startsWith("Password") ?: false,
                visualTransformation = remember { PasswordVisualTransformation() }
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

                        scope.launch {
                            inProgress = false

                            try {
                                val userSettings = apiService.loginOrRegister(User(username, password))
                                if (userSettings != null) {
                                    database.userQueries.login(username, password)
                                    userState.value = userSettings
                                } else {
                                    error = "Failed to login. Try again"
                                }
                            } catch (e : Exception) {
                                error = e.message
                            }

                            inProgress = false
                        }
                    }
                }
            }) {
                if (inProgress) {
                    Text("Waiting...")
                } else {
                    Text("Login/Register")
                }
            }
        }
    }
}
