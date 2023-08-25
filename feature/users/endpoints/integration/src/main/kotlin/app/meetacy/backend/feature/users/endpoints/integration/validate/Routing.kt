package app.meetacy.backend.feature.users.endpoints.integration.validate

import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameRepository
import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameResult
import app.meetacy.backend.feature.users.endpoints.validate.validateUsername
import app.meetacy.backend.feature.users.usecase.validate.ValidateUsernameUsecase
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.validate() {
    val repository = object : ValidateUsernameRepository {
        val usecase: ValidateUsernameUsecase by di.getting
        override suspend fun validateUsername(username: String): ValidateUsernameResult = when (val result = usecase.validateUsername(username)) {
            ValidateUsernameUsecase.Result.InvalidUtf8String -> ValidateUsernameResult.InvalidValidateUsernameString
            ValidateUsernameUsecase.Result.UsernameAlreadyOccupied -> ValidateUsernameResult.AlreadyOccupiedUsername
            is ValidateUsernameUsecase.Result.Success -> ValidateUsernameResult.Success(result.username.serializable())
        }
    }

    validateUsername(repository)
}
