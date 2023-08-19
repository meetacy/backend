package app.meetacy.backend.application.database.location

import app.meetacy.backend.feature.friends.database.location.UsersLocationsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.usersLocationsStorage: UsersLocationsStorage by Dependency

fun DIBuilder.location() {
    val usersLocationsStorage by singleton { UsersLocationsStorage(database) }
}
