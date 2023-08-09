package app.meetacy.backend.feature.auth.endpoints

import app.meetacy.backend.feature.auth.endpoints.generate.generateToken
import app.meetacy.backend.feature.email.endpoints.email
import io.ktor.server.routing.*

fun Route.auth() = route("/auth") {
    email()
    generateToken()
}
