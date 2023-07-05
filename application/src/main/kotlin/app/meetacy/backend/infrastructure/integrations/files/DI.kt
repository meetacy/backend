@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.files

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.infrastructure.integrations.files.get.getFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.saveFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.uploadFileRepository

val DI.filesDependencies: FilesDependencies by Dependency

fun DIBuilder.files() {
    val filesDependencies by singleton { FilesDependencies(saveFileRepository, getFileRepository) }

    getFileRepository()
    uploadFileRepository()
}
