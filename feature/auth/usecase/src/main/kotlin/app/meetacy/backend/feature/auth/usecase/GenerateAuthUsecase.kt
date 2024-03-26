package app.meetacy.backend.feature.auth.usecase

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.types.utf8Checker.checkString


class GenerateAuthUsecase(
    private val storage: Storage,
    private val tokenGenerator: TokenGenerator,
    private val utf8Checker: Utf8Checker
) {

    suspend fun generateAuth(nickname: String): Result {
        utf8Checker.checkString(nickname) { return Result.InvalidUtf8String }
        val newUserId = storage.createUser(nickname.trim())
        val token = tokenGenerator.generateToken(newUserId)
        return Result.Success(token)
    }

    sealed interface Result {
        data class Success(val accessIdentity: AccessIdentity) : Result
        data object InvalidUtf8String : Result
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserId
        suspend fun addToken(accessIdentity: AccessIdentity)
    }

    interface TokenGenerator {
        suspend fun generateToken(userId: UserId): AccessIdentity
    }
}
