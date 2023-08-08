package app.meetacy.backend.feature.auth.endpoints

import app.meetacy.backend.feature.auth.endpoints.email.email
import app.meetacy.backend.feature.auth.endpoints.generate.generateToken
import io.ktor.server.routing.*

fun Route.auth() = route("/auth") {
    email()
    generateToken()
}
