package app.meetacy.backend.feature.users.usecase.integration.users.validate

import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameRepository
import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameResult
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.feature.users.usecase.validate.ValidateUsernameUsecase

class UsecaseValidateUsernameRepository(
    private val usecase: ValidateUsernameUsecase
) : ValidateUsernameRepository {
    override suspend fun validateUsername(username: String): ValidateUsernameResult =
        when(val result = usecase.validateUsername(username)) {
            ValidateUsernameUsecase.Result.UsernameAlreadyOccupied ->
                ValidateUsernameResult.AlreadyOccupiedUsername
            ValidateUsernameUsecase.Result.InvalidUtf8String ->
                ValidateUsernameResult.InvalidValidateUsernameString
            is ValidateUsernameUsecase.Result.Success ->
                ValidateUsernameResult.Success(result.username.serializable())
        }
}
