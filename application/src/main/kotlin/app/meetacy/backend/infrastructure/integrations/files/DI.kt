package app.meetacy.backend.infrastructure.integrations.files

import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.infrastructure.integrations.files.get.getFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.saveFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.uploadFileRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.filesDependencies: FilesDependencies by Dependency

fun DIBuilder.files() {
    val filesDependencies by singleton { FilesDependencies(saveFileRepository, getFileRepository) }

    getFileRepository()
    uploadFileRepository()
}
