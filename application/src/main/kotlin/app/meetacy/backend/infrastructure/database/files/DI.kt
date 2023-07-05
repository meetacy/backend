@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.files

import app.meetacy.backend.database.files.FilesStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.GettingDelegate
import app.meetacy.backend.infrastructure.database.database

val DI.filesStorage: FilesStorage by GettingDelegate

fun DIBuilder.files() {
    val filesStorage by singleton { FilesStorage(database) }
}
