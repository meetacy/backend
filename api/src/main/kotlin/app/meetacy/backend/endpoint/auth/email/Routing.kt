package app.meetacy.backend.endpoint.auth.email

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.endpoint.auth.email.confirm.confirmEmail
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.email.link.linkEmail
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.email(
    linkEmailRepository: LinkEmailRepository,
    confirmStorage: ConfirmStorage
) = route("/email") {
    linkEmail(linkEmailRepository)
    confirmEmail(confirmStorage)
}
