package app.meetacy.backend.application.usecase.users.validate

import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameRepository
import app.meetacy.backend.infrastructure.database.users.validate.validateUserRepository
import app.meetacy.backend.feature.users.usecase.integration.users.validate.UsecaseValidateUsernameRepository
import app.meetacy.backend.feature.users.usecase.validate.ValidateUsernameUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.validateUsernameRepository: ValidateUsernameRepository by Dependency

fun DIBuilder.validateUsernameRepository() {
    val validateUsernameRepository by singleton<ValidateUsernameRepository> {
        UsecaseValidateUsernameRepository(
            usecase = ValidateUsernameUsecase(
                validateRepository = validateUserRepository
            )
        )
    }
}
