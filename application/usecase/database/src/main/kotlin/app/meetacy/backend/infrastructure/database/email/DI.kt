@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.email

import app.meetacy.backend.database.email.ConfirmationStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmationStorage: ConfirmationStorage by Dependency

fun DIBuilder.email() {
    val confirmationStorage by singleton { ConfirmationStorage(database) }
}
