package app.meetacy.backend.usecase.auth

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.HashGenerator

class GenerateTokenUsecase(private val storage: Storage, private val tokenGenerator: HashGenerator) {

    suspend fun generateToken(nickname: String): AccessToken {
        val newUserId = storage.createUser(nickname)
        val token = AccessToken(tokenGenerator.generate())
        storage.addToken(newUserId, token)
        return token
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserId
        suspend fun addToken(id: UserId, token: AccessToken)
    }
}
