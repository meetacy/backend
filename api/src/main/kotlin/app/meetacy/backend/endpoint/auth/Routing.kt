package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.generate.getToken
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.auth() = route("/auth") {
    email()
    getToken()
}
