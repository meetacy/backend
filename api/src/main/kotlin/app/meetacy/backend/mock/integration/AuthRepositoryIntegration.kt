package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.types.AuthRepository

object MockAuthRepository : AuthRepository {
    override suspend fun authorize(accessToken: AccessToken): AuthRepository.Result =
        when (val result = TokensStorage.getToken(accessToken)?.ownerId) {
            null -> AuthRepository.Result.TokenInvalid
            else -> AuthRepository.Result.Success(result)
        }
}
