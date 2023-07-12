@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.users

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.integrations.users.edit.editUserRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserRepository
import app.meetacy.backend.infrastructure.integrations.users.username.username
import app.meetacy.backend.infrastructure.integrations.users.username.usernameDependencies

val DI.usersDependencies: UsersDependencies by Dependency

fun DIBuilder.users() {
    getUserRepository()
    editUserRepository()
    username()
    val usersDependencies by singleton {
        UsersDependencies(
            getUserRepository,
            editUserRepository,
            usernameDependencies
        )
    }
}
