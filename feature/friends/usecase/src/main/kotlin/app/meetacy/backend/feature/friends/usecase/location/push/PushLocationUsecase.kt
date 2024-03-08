package app.meetacy.backend.feature.friends.usecase.location.push

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.users.UserId

class PushLocationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository
) {
    suspend fun push(accessIdentity: AccessIdentity, location: Location): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }
        storage.setLocation(userId, location)
        return Result.Success
    }

    sealed interface Result {
        data object TokenInvalid : Result
        data object Success : Result
    }

    interface Storage {
        suspend fun setLocation(userId: UserId, location: Location)
    }
}
