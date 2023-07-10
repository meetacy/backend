package app.meetacy.backend.endpoint.auth.email

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.endpoint.auth.email.confirm.confirmEmail
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.email.link.linkEmail
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

class EmailDependencies(
    val linkEmailRepository: LinkEmailRepository,
    val confirmEmailRepository: ConfirmEmailRepository
)

fun Route.email(emailDependencies: EmailDependencies) = route("/email") {
    linkEmail(emailDependencies.linkEmailRepository)
    confirmEmail(emailDependencies.confirmEmailRepository)
}
