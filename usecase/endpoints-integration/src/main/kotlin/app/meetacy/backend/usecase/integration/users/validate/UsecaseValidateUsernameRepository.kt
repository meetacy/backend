package app.meetacy.backend.usecase.integration.users.validate

import app.meetacy.backend.endpoint.users.username.validate.ValidateUsernameRepository
import app.meetacy.backend.endpoint.users.username.validate.ValidateUsernameResult
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.validate.ValidateUsernameUsecase

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
