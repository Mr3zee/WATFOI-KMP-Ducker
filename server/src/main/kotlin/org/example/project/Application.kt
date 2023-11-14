package org.example.project

import SERVER_PORT
import User
import UserSettings
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

val service by lazy {
    AppService()
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route("/api") {
            post("/loginOrRegister") {
                val user = call.receive<User>()
                val settings = service.loginOrRegister(user)
                if (settings != null) {
                    call.respond(settings)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }

            post("/update") {
                val settings = call.receive<UserSettings>()
                if (service.login(settings.user)) {
                    service.update(settings)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }

            get("/leaderboard") {
                call.respond(service.getLeaderboard())
            }
        }
    }
}
