package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.link.Mailer
import app.meetacy.backend.endpoint.auth.generate.TokenGenerator
import app.meetacy.backend.endpoint.auth.generate.generateToken
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.auth(
    mailer: Mailer,
    linkEmailStorage: LinkEmailStorage,
    confirmStorage: ConfirmStorage,
    tokenGenerator: TokenGenerator
) = route("/auth") {
    email(mailer, linkEmailStorage, confirmStorage)
    generateToken(tokenGenerator)
}
