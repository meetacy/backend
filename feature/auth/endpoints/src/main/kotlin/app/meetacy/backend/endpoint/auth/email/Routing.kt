package app.meetacy.backend.endpoint.auth.email

import app.meetacy.backend.endpoint.auth.email.confirm.confirmEmail
import app.meetacy.backend.endpoint.auth.email.link.linkEmail
import io.ktor.server.routing.*

fun Route.email() = route("/email") {
    linkEmail()
    confirmEmail()
}
