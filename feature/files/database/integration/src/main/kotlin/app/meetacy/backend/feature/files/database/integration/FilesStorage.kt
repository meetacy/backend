package app.meetacy.backend.feature.files.database.integration

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.filesStorage() {
    val filesStorage by singleton {
        val database: Database by getting
        FilesStorage(database)
    }
}
