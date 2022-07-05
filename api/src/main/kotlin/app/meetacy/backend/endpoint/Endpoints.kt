package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val login: String,
    val password: String
)

@Serializable
data class Status(
    val status: Boolean
)

fun startEndpoints(port: Int, wait: Boolean) =
    embeddedServer(CIO, port) {
        install(ContentNegotiation) {
            json()
        }

        routing {
            post("/auth/") {
                val credentials: Credentials = call.receive()
                call.respond(Status(credentials.login == "admin" && credentials.password == "admin"))
            }
        }
    }.start(wait)

