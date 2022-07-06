package app.meetacy.backend.endpoint

import app.meetacy.backend.endpoint.auth.AuthProvider
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.Serializable
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.auth
import app.meetacy.backend.endpoint.ok.ok
import app.meetacy.backend.endpoint.users.UsersProvider
import app.meetacy.backend.endpoint.users.demoUsers

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
    authProvider: AuthProvider,
    usersProvider: UsersProvider
) = embeddedServer(CIO, port) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        ok()
        auth(authProvider)
        demoUsers(usersProvider)
    }
}.start(wait)
