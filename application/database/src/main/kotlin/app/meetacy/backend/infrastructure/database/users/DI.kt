package app.meetacy.backend.infrastructure.database.users

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.users.edit.editUser
import app.meetacy.backend.infrastructure.database.users.get.getUser
import app.meetacy.backend.infrastructure.database.users.validate.validateUser
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.usersStorage: UsersStorage by Dependency

fun DIBuilder.users() {
    editUser()
    getUser()
    validateUser()
    val usersStorage by singleton { UsersStorage(database) }
}
