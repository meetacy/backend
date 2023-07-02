package app.meetacy.backend.infrastructure.factories.files.get

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import org.jetbrains.exposed.sql.Database

fun getFileRepository(db: Database, filesBasePath: String): GetFileRepository = DatabaseGetFileRepository(
    database = db,
    basePath = filesBasePath
)


