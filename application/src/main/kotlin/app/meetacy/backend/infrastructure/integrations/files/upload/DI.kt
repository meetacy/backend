package app.meetacy.backend.infrastructure.integrations.files.upload

import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.files.upload.uploadFileUsecase
import app.meetacy.backend.infrastructure.filesBasePath
import app.meetacy.backend.infrastructure.filesLimitPerUser
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.saveFileRepository: SaveFileRepository by Dependency

fun DIBuilder.uploadFileRepository() {
    val uploadFileRepository by singleton<SaveFileRepository> {
        UsecaseUploadFileRepository(
            usecase = UploadFileUsecase(
                authRepository = authRepository,
                storage = uploadFileUsecase,
                hashGenerator = get()
            ),
            basePath = filesBasePath,
            filesLimit = filesLimitPerUser,
            deleteFilesOnExit = false
        )
    }
}
