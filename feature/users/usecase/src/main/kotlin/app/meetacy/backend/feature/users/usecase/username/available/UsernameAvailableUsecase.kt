package app.meetacy.backend.feature.users.usecase.username.available

import app.meetacy.backend.types.users.Username

class UsernameAvailableUsecase(
    private val storage: Storage
) {
    sealed interface Result {
        data class Success(val username: Username) : Result
        data object InvalidUtf8String : Result
        data object UsernameAlreadyOccupied : Result
    }

    suspend fun usernameAvailable(username: String): Result {
        val parsedUsername = Username.parseOrNull(username) ?: return Result.InvalidUtf8String
        if (storage.isOccupied(parsedUsername)) return Result.UsernameAlreadyOccupied
        return Result.Success(parsedUsername)
    }

    interface Storage {
        suspend fun isOccupied(username: Username): Boolean
    }
}
