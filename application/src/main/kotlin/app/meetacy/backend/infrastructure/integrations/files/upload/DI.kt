@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.files.upload

import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.filesBasePath
import app.meetacy.backend.infrastructure.filesLimitPerUser
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository

val DI.saveFileRepository: SaveFileRepository by Dependency

fun DIBuilder.uploadFileRepository() {
    val uploadFileRepository by singleton<SaveFileRepository> {
        UsecaseUploadFileRepository(
            usecase = UploadFileUsecase(
                authRepository = authRepository,
                storage = DatabaseUploadFileStorage(database),
                hashGenerator = DefaultHashGenerator
            ),
            basePath = filesBasePath,
            filesLimit = filesLimitPerUser,
            deleteFilesOnExit = false
        )
    }
}
