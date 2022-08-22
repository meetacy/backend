package app.meetacy.backend.endpoint.auth

import app.meetacy.backend.endpoint.auth.email.EmailDependencies
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.endpoint.auth.email.email
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.endpoint.auth.generate.generateToken
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

class AuthDependencies(
    val emailDependencies: EmailDependencies,
    val tokenGenerateRepository: TokenGenerateRepository
)

fun Route.auth(dependencies: AuthDependencies) = route("/auth") {
    email(dependencies.emailDependencies)
    generateToken(dependencies.tokenGenerateRepository)
}
