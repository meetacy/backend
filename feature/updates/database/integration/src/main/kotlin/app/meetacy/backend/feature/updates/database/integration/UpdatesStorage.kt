package app.meetacy.backend.feature.updates.database.integration

import app.meetacy.backend.feature.updates.database.updates.UpdatesStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.updatesStorage() {
    val updatesStorage by singleton {
        val database: Database by getting
        UpdatesStorage(database)
    }
}
