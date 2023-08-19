package app.meetacy.backend.application.database.users.validate

import app.meetacy.backend.feature.users.database.integration.types.DatabaseValidateRepository
import app.meetacy.backend.application.database.database
import app.meetacy.backend.types.users.ValidateRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.validateUserRepository: ValidateRepository by Dependency

fun DIBuilder.validateUser() {
    val validateUserRepository by singleton<ValidateRepository> {
        DatabaseValidateRepository(database)
    }
}
