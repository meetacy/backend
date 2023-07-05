@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.email

import app.meetacy.backend.database.email.ConfirmationStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.confirmationStorage: ConfirmationStorage by Dependency

fun DIBuilder.email() {
    val confirmationStorage by singleton { ConfirmationStorage(database) }
}
