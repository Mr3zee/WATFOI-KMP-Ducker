import androidx.compose.runtime.mutableStateOf

class User(val name: String)

class Model(val user: User) {
    val conterValue = mutableStateOf(0)
    val selectedDuck = mutableStateOf(DuckType.T_SHIRT)
}
