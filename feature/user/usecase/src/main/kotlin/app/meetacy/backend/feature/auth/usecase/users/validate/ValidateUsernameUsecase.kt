package app.meetacy.backend.feature.auth.usecase.users.validate

import app.meetacy.backend.feature.auth.usecase.types.ValidateRepository
import app.meetacy.backend.feature.auth.usecase.types.isOccupied
import app.meetacy.backend.types.user.Username

class ValidateUsernameUsecase(
    private val validateRepository: ValidateRepository
) {
    sealed interface Result {
        class Success(val username: Username) : Result
        object InvalidUtf8String : Result
        object UsernameAlreadyOccupied : Result
    }

    suspend fun validateUsername(username: String): Result {
        val parsedUsername = Username.parseOrNull(username) ?: return Result.InvalidUtf8String
        validateRepository.isOccupied(parsedUsername) { return Result.UsernameAlreadyOccupied }
        return Result.Success(parsedUsername)
    }
}
