@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.files

import app.meetacy.backend.database.files.FilesStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.filesStorage: FilesStorage by Dependency

fun DIBuilder.files() {
    val filesStorage by singleton { FilesStorage(database) }
}
