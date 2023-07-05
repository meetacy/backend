package app.meetacy.backend.infrastructure.integrations.files.upload

import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun uploadFileRepository(
    db: Database,
    filesBasePath: String,
    filesLimit: Long,
    authRepository: AuthRepository = authRepository(db)
): SaveFileRepository = UsecaseUploadFileRepository(
    usecase = UploadFileUsecase(
        authRepository = authRepository,
        storage = DatabaseUploadFileStorage(db),
        hashGenerator = DefaultHashGenerator
    ),
    basePath = filesBasePath,
    filesLimit = filesLimit,
    deleteFilesOnExit = false
)
