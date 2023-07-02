package app.meetacy.backend.infrastructure.factories.files.get

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.di.DIBuilder
import app.meetacy.backend.infrastructure.baseUrl

fun DIBuilder.getFileRepository() = singleton {
    DatabaseGetFileRepository(get(), baseUrl)
}
