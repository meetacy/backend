package app.meetacy.backend.usecase.validate

import app.meetacy.backend.types.user.Username
import app.meetacy.backend.usecase.types.ValidateRepository
import app.meetacy.backend.usecase.types.isOccupied

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
