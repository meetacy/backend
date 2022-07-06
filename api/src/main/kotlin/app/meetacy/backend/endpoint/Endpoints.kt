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
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.MockAuthProvider
import app.meetacy.backend.endpoint.users.MockUsersProvider
import io.ktor.server.response.*

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
            get("/OK") {
                call.respondText("OK")
            }
            post("/auth") {
                val credentials: Credentials = call.receive()
                val result = MockAuthProvider.authorize(credentials)
                call.respond(result)
            }
            get("/demo-users") {
                call.respond(MockUsersProvider.getUsers())
            }
        }
    }.start(wait)
