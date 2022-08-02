package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.endpoint.auth.generate.generateToken
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.auth(
    linkEmailRepository: LinkEmailRepository,
    confirmEmailRepository: ConfirmEmailRepository,
    tokenGenerateRepository: TokenGenerateRepository
) = route("/auth") {
    email(linkEmailRepository, confirmEmailRepository)
    generateToken(tokenGenerateRepository)
}
