package app.meetacy.backend.infrastructure.usecase.auth

import app.meetacy.backend.infrastructure.usecase.auth.email.email
import app.meetacy.backend.infrastructure.usecase.auth.tokenGenerator.tokenGenerator
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.auth() {
    email()
    tokenGenerator()
}
