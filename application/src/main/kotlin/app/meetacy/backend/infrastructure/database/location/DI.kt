@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.location

import app.meetacy.backend.database.location.UsersLocationsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.usersLocationsStorage: UsersLocationsStorage by Dependency

fun DIBuilder.location() {
    val usersLocationsStorage by singleton { UsersLocationsStorage(database) }
}
