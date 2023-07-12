package app.meetacy.backend.infrastructure.integrations.users.username

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.users.username.UsernameDependencies
import app.meetacy.backend.infrastructure.integrations.users.username.validate.validateUsernameRepository

val DI.usernameDependencies: UsernameDependencies by Dependency

fun DIBuilder.username() {
    validateUsernameRepository()
    val usernameDependencies by singleton {
        UsernameDependencies(validateUsernameRepository)
    }
}
