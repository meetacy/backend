package app.meetacy.backend.application.endpoints.files

import app.meetacy.backend.application.usecase.files.get.getFileRepository
import app.meetacy.backend.application.usecase.files.upload.uploadFileRepository
import app.meetacy.backend.types.file.FileSize
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.filesBasePath: String by Dependency
val DI.filesLimitPerUser: FileSize by Dependency

fun DIBuilder.files() {
    getFileRepository()
    uploadFileRepository()
}
