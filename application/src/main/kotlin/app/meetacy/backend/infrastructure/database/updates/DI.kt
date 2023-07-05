@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.updates

import app.meetacy.backend.database.updates.UpdatesStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.updatesStorage: UpdatesStorage by Dependency

fun DIBuilder.updates() {
    val updatesStorage by singleton { UpdatesStorage(database) }
}
