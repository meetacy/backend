package app.meetacy.backend.infrastructure.integration.auth

import app.meetacy.backend.infrastructure.integration.auth.email.email
import app.meetacy.backend.infrastructure.integration.auth.tokenGenerator.tokenGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    email()
    tokenGenerator()
}
