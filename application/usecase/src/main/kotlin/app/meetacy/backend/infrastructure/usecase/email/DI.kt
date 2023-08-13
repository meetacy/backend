package app.meetacy.backend.infrastructure.usecase.email

import app.meetacy.backend.infrastructure.usecase.email.confirm.confirmEmailRepository
import app.meetacy.backend.infrastructure.usecase.email.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
}
