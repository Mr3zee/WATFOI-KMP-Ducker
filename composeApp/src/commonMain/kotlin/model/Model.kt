package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import storage.Database
import storage.LoggedUser
import utils.DuckType

class Model(private val user: MutableState<LoggedUser?>, private val database: Database) {
    val username: String? get() = user.value?.name

    val conterValue = mutableStateOf(0)
    val selectedDuck = mutableStateOf(DuckType.T_SHIRT)

    fun logout() {
        database.userQueries.loggout()
        user.value = null
    }
}
