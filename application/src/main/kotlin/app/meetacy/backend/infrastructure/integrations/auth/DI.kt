package app.meetacy.backend.infrastructure.integrations.auth

import app.meetacy.backend.infrastructure.integrations.auth.email.email
import app.meetacy.backend.infrastructure.integrations.auth.tokenGenerator.tokenGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    email()
    tokenGenerator()
}
