package app.meetacy.backend.application.endpoints.email

import app.meetacy.backend.application.endpoints.email.confirm.confirmEmailRepository
import app.meetacy.backend.application.endpoints.email.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
}
