package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.di
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.files.files

val DI.baseUrl: String by Dependency
val DI.filesDirectory: String by Dependency

fun di() = di {
    database()
    files()
}
