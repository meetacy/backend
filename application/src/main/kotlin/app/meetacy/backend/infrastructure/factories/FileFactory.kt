package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun fileDependenciesFactory(
    db: Database,
    authRepository: AuthRepository,
    filesBasePath: String,
    filesLimit: Long
): FilesDependencies = FilesDependencies(
    saveFileRepository = UsecaseUploadFileRepository(
        usecase = UploadFileUsecase(
            authRepository = authRepository,
            storage = DatabaseUploadFileStorage(db),
            hashGenerator = DefaultHashGenerator
        ),
        basePath = filesBasePath,
        filesLimit = filesLimit,
        deleteFilesOnExit = false
    ),
    getFileRepository = DatabaseGetFileRepository(
        database = db,
        basePath = filesBasePath
    )
)
