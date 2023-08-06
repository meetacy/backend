package app.meetacy.backend.infrastructure.integrations.auth.email

import app.meetacy.backend.infrastructure.integrations.auth.email.confirm.confirmEmailRepository
import app.meetacy.backend.infrastructure.integrations.auth.email.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmEmailRepository()
    linkEmailRepository()
}
