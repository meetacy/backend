package app.meetacy.backend.infrastructure.integration.auth

import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.infrastructure.integration.auth.email.email
import app.meetacy.backend.infrastructure.integration.auth.email.emailDependencies
import app.meetacy.backend.infrastructure.integration.auth.tokenGenerator.tokenGenerateRepository
import app.meetacy.backend.infrastructure.integration.auth.tokenGenerator.tokenGenerator
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    email()
    tokenGenerator()
}
