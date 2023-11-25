package app.meetacy.backend.feature.email.database.integration

import app.meetacy.backend.feature.email.database.integration.confirmationStorage
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.email() {
    confirmationStorage()
}
