package app.meetacy.backend.infrastructure.factories.files

import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.usecase.types.FilesRepository
import org.jetbrains.exposed.sql.Database

fun filesRepository(db: Database): FilesRepository = DatabaseFilesRepository(db)
