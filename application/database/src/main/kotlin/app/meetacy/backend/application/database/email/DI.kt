package app.meetacy.backend.application.database.email

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.database.email.confirm.confirmEmail
import app.meetacy.backend.application.database.email.link.linkEmail
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmationStorage: ConfirmationStorage by Dependency

fun DIBuilder.email() {
    confirmEmail()
    linkEmail()
    val confirmationStorage by singleton { ConfirmationStorage(database) }
}
