package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.Credentials
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post


fun Routing.auth(authProvider: AuthProvider) = post("/auth") {
    val credentials: Credentials = call.receive()
    val result = authProvider.authorize(credentials)
    call.respond(result)
}
