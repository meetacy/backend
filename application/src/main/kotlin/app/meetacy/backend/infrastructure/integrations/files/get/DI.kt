@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.files.get

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.infrastructure.database.files.filesStorage
import app.meetacy.backend.infrastructure.filesDirectory

val DI.getFileRepository: GetFileRepository by Dependency

fun DIBuilder.getFileRepository() {
    val getFilesRepository by singleton {
        DatabaseGetFileRepository(filesStorage, filesDirectory)
    }
}
