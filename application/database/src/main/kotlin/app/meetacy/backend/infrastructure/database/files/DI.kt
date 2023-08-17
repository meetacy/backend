package app.meetacy.backend.infrastructure.database.files

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.feature.files.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.files.upload.uploadFile
import app.meetacy.backend.feature.files.usecase.types.FilesRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.filesStorage: FilesStorage by Dependency
val DI.filesRepository: FilesRepository by Dependency

fun DIBuilder.files() {
    uploadFile()
    val filesRepository by singleton<FilesRepository> { DatabaseFilesRepository(database) }
    val filesStorage by singleton { FilesStorage(database) }
}
