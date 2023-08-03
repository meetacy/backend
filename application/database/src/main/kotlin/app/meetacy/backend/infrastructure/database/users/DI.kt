@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.users

import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.usersStorage: UsersStorage by Dependency

fun DIBuilder.users() {
    val usersStorage by singleton { UsersStorage(database) }
}
