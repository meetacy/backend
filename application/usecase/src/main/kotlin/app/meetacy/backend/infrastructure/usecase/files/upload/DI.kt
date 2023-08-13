package app.meetacy.backend.infrastructure.usecase.files.upload

import app.meetacy.backend.feature.files.endpoints.upload.SaveFileRepository
import app.meetacy.backend.infrastructure.database.files.upload.uploadFileUsecase
import app.meetacy.backend.infrastructure.usecase.files.filesBasePath
import app.meetacy.backend.infrastructure.usecase.files.filesLimitPerUser
import app.meetacy.backend.feature.files.usecase.files.UploadFileUsecase
import app.meetacy.backend.feature.files.usecase.integration.UsecaseUploadFileRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.saveFileRepository: SaveFileRepository by Dependency

fun DIBuilder.uploadFileRepository() {
    val uploadFileRepository by singleton<SaveFileRepository> {
        UsecaseUploadFileRepository(
            usecase = UploadFileUsecase(
                authRepository = get(),
                storage = uploadFileUsecase,
                hashGenerator = get()
            ),
            basePath = filesBasePath,
            filesLimit = filesLimitPerUser,
            deleteFilesOnExit = false
        )
    }
}
