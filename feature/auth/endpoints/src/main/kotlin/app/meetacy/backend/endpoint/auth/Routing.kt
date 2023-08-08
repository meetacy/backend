package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.generate.generateToken
import app.meetacy.backend.feature.email.endpoints.email
import io.ktor.server.routing.*

fun Route.auth() = route("/auth") {
    email()
    generateToken()
}
