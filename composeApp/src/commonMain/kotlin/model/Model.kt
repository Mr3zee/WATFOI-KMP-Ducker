package model

import UserSettings
import androidx.compose.runtime.*
import api.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import storage.Database
import utils.DuckType

class Model private constructor(private val userSettings: MutableState<UserSettings?>, private val database: Database) {
    private val user = userSettings.value?.user ?: error("Expected user")

    val username: String? get() = userSettings.value?.user?.name

    val conterValue = mutableStateOf(userSettings.value?.counterValue ?: 0)
    val selectedDuck = mutableStateOf(userSettings.value?.duckType?.let { DuckType.valueOf(it) } ?: DuckType.T_SHIRT)

    fun logout() {
        database.userQueries.loggout()
        userSettings.value = null
    }

    companion object {
        @Composable
        fun create(userSettings: MutableState<UserSettings?>, database: Database): Model {
            val model = remember { Model(userSettings, database) }

            LaunchedEffect(model.conterValue.value, model.selectedDuck.value) {
                apiService.update(UserSettings(model.user, model.conterValue.value, model.selectedDuck.value.name))
            }

            return model
        }
    }
}
