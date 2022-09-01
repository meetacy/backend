package app.meetacy.backend.usecase.auth

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserIdentity

class GenerateTokenUsecase(
    private val storage: Storage,
) {

    suspend fun generateToken(nickname: String): AccessIdentity {
        val newUser = storage.createUser(nickname)
        val token = AccessIdentity(
            newUser.userId,
            AccessToken(newUser.accessHash.string)
        )
        storage.addToken(token)
        return token
    }

    interface Storage {
        suspend fun createUser(nickname: String): UserIdentity
        suspend fun addToken(accessIdentity: AccessIdentity)
    }
}
