package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.generate.TokenGenerator
import app.meetacy.backend.endpoint.auth.generate.generateToken
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.auth(
    linkEmailRepository: LinkEmailRepository,
    confirmEmailRepository: ConfirmEmailRepository,
    tokenGenerator: TokenGenerator
) = route("/auth") {
    email(linkEmailRepository, confirmEmailRepository)
    generateToken(tokenGenerator)
}
