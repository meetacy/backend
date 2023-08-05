package app.meetacy.backend.infrastructure.integrations.users

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.integrations.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserRepository
import app.meetacy.backend.infrastructure.integrations.users.validate.validateUsernameRepository

val DI.usersDependencies: UsersDependencies by Dependency

fun DIBuilder.users() {
    getUserRepository()
    editUserRepository()
    validateUsernameRepository()
    val usersDependencies by singleton {
        UsersDependencies(
            getUserRepository,
            editUserRepository
        )
    }
}
