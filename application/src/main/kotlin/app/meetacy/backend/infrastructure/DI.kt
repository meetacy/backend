package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.di
import app.meetacy.backend.di.dependency.GettingDelegate
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.factories.files.files

val DI.baseUrl: String by GettingDelegate
val DI.filesDirectory: String by GettingDelegate

fun di() = di {
    database()
    files()
}
