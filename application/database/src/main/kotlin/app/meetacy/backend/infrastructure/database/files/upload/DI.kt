package app.meetacy.backend.infrastructure.database.files.upload

import app.meetacy.backend.feature.files.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.feature.files.usecase.files.UploadFileUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.uploadFileUsecase: UploadFileUsecase.Storage by Dependency

fun DIBuilder.uploadFile() {
    val uploadFileUsecase by singleton<UploadFileUsecase.Storage> {
        DatabaseUploadFileStorage(database)
    }
}
