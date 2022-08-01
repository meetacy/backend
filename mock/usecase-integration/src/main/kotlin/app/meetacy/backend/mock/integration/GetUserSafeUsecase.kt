package app.meetacy.backend.mock.integration

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

object MockGetUserSafeStorage : GetUserSafeUsecase.Storage {
    override fun getTokenOwnerId(token: AccessToken) = TokensStorage.getToken(token)?.ownerId
}
