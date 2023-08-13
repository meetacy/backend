package app.meetacy.backend.infrastructure.usecase

import app.meetacy.backend.feature.auth.usecase.integration.auth
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.usecase() {
    auth()
}
