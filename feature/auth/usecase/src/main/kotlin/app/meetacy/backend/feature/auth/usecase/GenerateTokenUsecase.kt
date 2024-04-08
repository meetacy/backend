package app.meetacy.backend.feature.auth.usecase

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.users.UserId


class GenerateTokenUsecase(
    private val storage: Storage,
    private val tokenGenerator: TokenGenerator
) {

    suspend fun generateToken(userId: UserId): AccessIdentity {
        val token = AccessIdentity(
            userId = userId,
            accessToken = tokenGenerator.generate()
        )
        storage.addToken(token)
        return token
    }

    interface Storage {
        suspend fun addToken(accessIdentity: AccessIdentity)
    }

    fun interface TokenGenerator {
        fun generate(): AccessToken
    }
}
