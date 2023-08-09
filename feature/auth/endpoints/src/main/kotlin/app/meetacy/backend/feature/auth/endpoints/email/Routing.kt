package app.meetacy.backend.feature.auth.endpoints.email

import app.meetacy.backend.feature.auth.endpoints.email.confirm.confirmEmail
import app.meetacy.backend.feature.auth.endpoints.email.link.linkEmail
import io.ktor.server.routing.*

fun Route.email() = route("/email") {
    linkEmail()
    confirmEmail()
}
