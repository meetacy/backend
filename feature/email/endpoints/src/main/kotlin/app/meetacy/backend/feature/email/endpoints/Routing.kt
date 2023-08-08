package app.meetacy.backend.feature.email.endpoints

import app.meetacy.backend.feature.email.endpoints.confirm.confirmEmail
import app.meetacy.backend.feature.email.endpoints.link.linkEmail
import io.ktor.server.routing.*

fun Route.email() = route("/email") {
    linkEmail()
    confirmEmail()
}
