package org.example.project

import Leaderboard
import LeaderboardEntry
import User
import UserSettings
import org.jetbrains.exposed.sql.*

class AppService {
    suspend fun loginOrRegister(user: User): UserSettings? {
        val maybeSettings = login(user)
        return when {
            maybeSettings != null -> maybeSettings

            exists(user) -> null

            else -> tr {
                Users.insert {
                    it[username] = user.name
                    it[password] = user.password
                }

                UserSettings(user, 0, null)
            }
        }
    }

    private suspend fun exists(user: User): Boolean = tr {
        !Users
            .slice(Users.username)
            .select {
                (Users.username eq user.name)
            }.empty()
    }

    suspend fun login(user: User): UserSettings? = tr {
        Users
            .slice(Users.counter, Users.duckType)
            .select {
                (Users.username eq user.name) and (Users.password eq user.password)
            }
            .singleOrNull()
            ?.let {
                UserSettings(user, it[Users.counter], it[Users.duckType])
            }
    }

    suspend fun getLeaderboard(): Leaderboard = tr {
        Leaderboard(
            Users.slice(Users.username, Users.counter).selectAll().map {
                LeaderboardEntry(it[Users.username], it[Users.counter])
            }
        )
    }

    suspend fun update(settings: UserSettings) {
        if (settings.counterValue == null && settings.duckType == null) {
            return
        }

        tr {
            Users.update(
                where = {
                    (Users.username eq settings.user.name) and (Users.password eq settings.user.password)
                }
            ) {
                settings.counterValue?.let { newValue ->
                    it[counter] = newValue
                }
                settings.duckType?.let { newValue ->
                    it[duckType] = newValue
                }
            }
        }
    }
}
