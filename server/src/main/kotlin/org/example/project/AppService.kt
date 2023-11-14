package org.example.project

import Leaderboard
import LeaderboardEntry
import User
import UserSettings

class AppService {
    private val users = mutableListOf<User>()
    private val settings = mutableMapOf<String, Pair<Int, String?>>()

    fun loginOrRegister(user: User): UserSettings? {
        val settings = when {
            login(user) -> settings[user.name]
            exists(user) -> null
            else -> {
                users.add(user)
                (0 to null).also {
                    settings[user.name] = it
                }
            }
        } ?: return null
        
        return UserSettings(user, settings.first, settings.second)
    }

    private fun exists(user: User): Boolean {
        return users.find { it.name == user.name } != null
    }
    
    fun login(user: User): Boolean {
        return users.contains(user)
    }

    fun getLeaderboard(): Leaderboard {
        return Leaderboard(users.map { LeaderboardEntry(it.name, settings[it.name]?.first ?: 0) })
    }

    fun update(settings: UserSettings) {
        this.settings.merge(settings.user.name, (settings.counterValue ?: -1) to settings.duckType) { old, new ->
            val counter = if (new.first == -1) {
                old.first
            } else new.first
            
            val duckType = if (new.second == null) {
                old.second
            } else new.second

            counter to duckType
        }
    }
}
