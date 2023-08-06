package app.meetacy.backend.infrastructure.integration.files.get

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.infrastructure.database.files.filesStorage
import app.meetacy.backend.infrastructure.filesBasePath
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getFileRepository: GetFileRepository by Dependency

fun DIBuilder.getFileRepository() {
    val getFileRepository by singleton<GetFileRepository> {
        DatabaseGetFileRepository(filesStorage, filesBasePath)
    }
}
