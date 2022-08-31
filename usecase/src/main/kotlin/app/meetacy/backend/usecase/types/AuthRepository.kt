package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity

interface AuthRepository {
    suspend fun authorize(accessToken: AccessToken): Result

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val userIdentity: UserId) : Result
    }
}

suspend inline fun AuthRepository.authorize(
    accessToken: AccessToken,
    fallback: () -> Nothing
): UserId = when (val result = authorize(accessToken)) {
    is AuthRepository.Result.Success -> result.userIdentity
    is AuthRepository.Result.TokenInvalid -> fallback()
}
