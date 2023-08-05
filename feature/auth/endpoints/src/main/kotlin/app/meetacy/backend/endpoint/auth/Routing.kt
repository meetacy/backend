package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.generate.generateToken
import io.ktor.server.routing.*

fun Route.auth() = route("/auth") {
    email()
    generateToken()
}
