package app.meetacy.backend.feature.users.usecase.validate

import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.users.ValidateRepository
import app.meetacy.backend.types.users.isOccupied

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
