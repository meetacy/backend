@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.files

import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.files.get.getFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.saveFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.uploadFileRepository
import app.meetacy.backend.usecase.types.FilesRepository

val DI.filesDependencies: FilesDependencies by Dependency
val DI.filesRepository: FilesRepository by Dependency

fun DIBuilder.files() {
    val filesDependencies by singleton { FilesDependencies(saveFileRepository, getFileRepository) }
    val filesRepository by singleton<FilesRepository> { DatabaseFilesRepository(database) }

    getFileRepository()
    uploadFileRepository()
}
