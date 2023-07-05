package app.meetacy.backend.infrastructure.integrations.files

import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.infrastructure.integrations.files.get.getFileRepository
import app.meetacy.backend.infrastructure.integrations.files.upload.uploadFileRepository
import org.jetbrains.exposed.sql.Database

fun fileDependenciesFactory(
    db: Database,
    filesBasePath: String,
    filesLimit: Long
): FilesDependencies = FilesDependencies(
    saveFileRepository = uploadFileRepository(db, filesBasePath, filesLimit),
    getFileRepository = getFileRepository(db, filesBasePath)
)

fun DIBuilder.files() {
    getFileRepository()
}
