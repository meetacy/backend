package app.meetacy.backend.usecase.auth

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.HashGenerator

class GenerateTokenUsecase(private val storage: Storage, private val tokenGenerator: HashGenerator) {

    suspend fun generateToken(nickname: String): AccessIdentity {
        val newUserId = storage.createUser(nickname)
        val token = AccessIdentity(
            newUserId,
            AccessToken(tokenGenerator.generate())
        )
        storage.addToken(token)
        return token
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserId
        suspend fun addToken(accessIdentity: AccessIdentity)
    }
}
