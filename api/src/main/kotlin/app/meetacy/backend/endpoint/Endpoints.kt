package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.Serializable
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.auth

@Serializable
data class Credentials(
    val login: String,
    val password: String
)

@Serializable
data class Status(
    val status: Boolean
)

fun startEndpoints(
    port: Int,
    wait: Boolean,
) = embeddedServer(CIO, port) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        auth()
    }
}.start(wait)
