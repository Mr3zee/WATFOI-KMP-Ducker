package api

import SERVER_PORT
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

expect val platformHost: String

val apiClient by lazy {
    HttpClient {
        install(ContentNegotiation) {
            json()
        }

        defaultRequest {
            url("http://$platformHost:$SERVER_PORT/")
        }
    }
}
