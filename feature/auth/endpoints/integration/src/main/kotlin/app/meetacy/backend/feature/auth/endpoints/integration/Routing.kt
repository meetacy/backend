package app.meetacy.backend.feature.auth.endpoints.integration

import app.meetacy.backend.feature.auth.endpoints.integration.generate.generateToken
import app.meetacy.backend.feature.email.endpoints.integration.email
import io.ktor.server.routing.*

fun Route.auth() = route("/auth") {
    email()
    generateToken()
}
