package app.meetacy.backend.feature.email.database.integration

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.confirmationStorage() {
    val confirmationStorage by singleton {
        val database: Database by getting
        ConfirmationStorage(database)
    }
}
