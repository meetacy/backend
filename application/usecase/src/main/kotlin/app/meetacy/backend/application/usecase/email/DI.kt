package app.meetacy.backend.application.usecase.email

import app.meetacy.backend.application.usecase.email.confirm.confirmEmailRepository
import app.meetacy.backend.application.usecase.email.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
}
