import androidx.compose.runtime.mutableStateOf

class Model {
    val conterValue = mutableStateOf(0)
    val selectedDuck = mutableStateOf(DuckType.T_SHIRT)
}
