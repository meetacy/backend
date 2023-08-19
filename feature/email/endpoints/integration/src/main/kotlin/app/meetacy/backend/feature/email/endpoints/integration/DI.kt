package app.meetacy.backend.feature.email.endpoints.integration

import app.meetacy.backend.feature.email.endpoints.integration.confirm.confirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.integration.link.linkEmailRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    linkEmailRepository()
    confirmEmailRepository()
}
