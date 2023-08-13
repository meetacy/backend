package app.meetacy.backend.infrastructure.usecase.files.get

import app.meetacy.backend.feature.files.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.feature.files.endpoints.download.GetFileRepository
import app.meetacy.backend.infrastructure.database.files.filesStorage
import app.meetacy.backend.infrastructure.usecase.files.filesBasePath
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getFileRepository: GetFileRepository by Dependency

fun DIBuilder.getFileRepository() {
    val getFileRepository by singleton<GetFileRepository> {
        DatabaseGetFileRepository(filesStorage, filesBasePath)
    }
}
