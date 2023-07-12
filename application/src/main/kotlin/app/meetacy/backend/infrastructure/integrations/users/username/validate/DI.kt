@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.users.username.validate

import app.meetacy.backend.database.integration.types.DatabaseValidateRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.users.username.validate.ValidateUsernameRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.integration.users.validate.UsecaseValidateUsernameRepository
import app.meetacy.backend.usecase.users.validate.ValidateUsernameUsecase

val DI.validateUsernameRepository: ValidateUsernameRepository by Dependency

fun DIBuilder.validateUsernameRepository() {
    val validateUsernameRepository by singleton<ValidateUsernameRepository> {
        UsecaseValidateUsernameRepository(
            usecase = ValidateUsernameUsecase(
                validateRepository = DatabaseValidateRepository(database)
            )
        )
    }
}
