package app.meetacy.backend.feature.auth.usecase.auth

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.types.utf8Checker.checkString


class GenerateTokenUsecase(
    private val storage: app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Storage,
    private val tokenGenerator: AccessHashGenerator,
    private val utf8Checker: Utf8Checker
) {

    suspend fun generateToken(nickname: String): app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result {
        utf8Checker.checkString(nickname) { return app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result.InvalidUtf8String }
        val newUserId = storage.createUser(nickname)
        val token = AccessIdentity(
            newUserId,
            AccessToken(tokenGenerator.generate())
        )
        storage.addToken(token)
        return app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result.Success(token)
    }

    sealed interface Result {
        class Success(val accessIdentity: AccessIdentity) :
            app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result
        object InvalidUtf8String : app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserId
        suspend fun addToken(accessIdentity: AccessIdentity)
    }
}
