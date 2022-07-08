package app.meetacy.backend.endpoint.auth.email

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.endpoint.auth.email.confirm.confirmEmail
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.link.Mailer
import app.meetacy.backend.endpoint.auth.email.link.linkEmail
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.email(
    mailer: Mailer,
    linkEmailStorage: LinkEmailStorage,
    confirmStorage: ConfirmStorage
) = route("/email") {
    linkEmail(mailer, linkEmailStorage)
    confirmEmail(confirmStorage)
}
