package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

private object GetUserStorage : GetUserSafeUsecase.Storage {
    override fun getTokenOwnerId(token: AccessToken) = TokensStorage.getToken(token)?.ownerId
}

fun mockGetUserUsecase(): GetUserSafeUsecase = GetUserSafeUsecase(GetUserStorage, mockGetUsersViewsUsecase())
