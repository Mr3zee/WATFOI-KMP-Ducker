package api

import Leaderboard
import User
import UserSettings
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

val apiService by lazy { ApiService() }

class ApiService {
    suspend fun loginOrRegister(user: User): UserSettings? {
        val response = apiClient.post("/api/loginOrRegister") {
            setBody(user)
            contentType(ContentType.Application.Json)
        }
        
        return when (response.status) {
            HttpStatusCode.OK -> response.body<UserSettings>()
            else -> null
        }
    }

    suspend fun update(settings: UserSettings) {
        apiClient.post("/api/update"){
            setBody(settings)
            contentType(ContentType.Application.Json)
        }
    }
    
    suspend fun getLeaderboard(): Leaderboard {
        return apiClient.get("/api/leaderboard").body()
    }
}
