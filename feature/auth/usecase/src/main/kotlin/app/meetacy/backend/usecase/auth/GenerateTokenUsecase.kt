package app.meetacy.backend.usecase.auth

import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.Utf8Checker
import app.meetacy.backend.usecase.types.checkString


class GenerateTokenUsecase(
    private val storage: Storage,
    private val tokenGenerator: AccessHashGenerator,
    private val utf8Checker: Utf8Checker
) {

    suspend fun generateToken(nickname: String): Result {
        utf8Checker.checkString(nickname) { return Result.InvalidUtf8String }
        val newUserId = storage.createUser(nickname)
        val token = AccessIdentity(
            newUserId,
            AccessToken(tokenGenerator.generate())
        )
        storage.addToken(token)
        return Result.Success(token)
    }

    sealed interface Result {
        class Success(val accessIdentity: AccessIdentity) : Result
        object InvalidUtf8String : Result
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserId
        suspend fun addToken(accessIdentity: AccessIdentity)
    }
}
