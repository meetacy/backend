package app.meetacy.backend.feature.email.endpoints.integration

import app.meetacy.backend.feature.email.endpoints.integration.confirm.confirmEmail
import app.meetacy.backend.feature.email.endpoints.integration.link.linkEmail
import io.ktor.server.routing.*

fun Route.email() = route("/email") {
    linkEmail()
    confirmEmail()
}
