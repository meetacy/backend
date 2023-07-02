@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.di
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.factories.files.files

val DI.baseUrl: String get() = get("baseUrl")

fun di() = di {
    database()
    files()
}
