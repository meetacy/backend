package app.meetacy.backend.infrastructure.usecase.auth.email

import app.meetacy.backend.infrastructure.usecase.auth.email.confirm.confirmEmailRepository
import app.meetacy.backend.infrastructure.usecase.auth.email.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
}
