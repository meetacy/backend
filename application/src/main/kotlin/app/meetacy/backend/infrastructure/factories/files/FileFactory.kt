package app.meetacy.backend.infrastructure.factories.files

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.endpoint.files.FilesDependencies
import org.jetbrains.exposed.sql.Database

fun fileDependenciesFactory(
    db: Database,
    filesBasePath: String,
    filesLimit: Long
): FilesDependencies = FilesDependencies(
    saveFileRepository = uploadFileRepository(db, filesBasePath, filesLimit),
    getFileRepository = DatabaseGetFileRepository(
        database = db,
        basePath = filesBasePath
    )
)
