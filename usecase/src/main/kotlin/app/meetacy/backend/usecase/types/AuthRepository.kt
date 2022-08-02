package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId

interface AuthRepository {
    suspend fun authorize(accessToken: AccessToken): Result

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val userId: UserId) : Result
    }
}

suspend inline fun AuthRepository.authorize(
    accessToken: AccessToken,
    fallback: () -> Nothing
): UserId = when (val result = authorize(accessToken)) {
    is AuthRepository.Result.Success -> result.userId
    is AuthRepository.Result.TokenInvalid -> fallback()
}
