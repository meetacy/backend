package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.users.GetUserUsecase

private object StorageIntegration : GetUserUsecase.Storage {
    override fun getTokenOwnerId(token: String) = TokensStorage.getToken(token)?.ownerId
    override fun getUser(id: Long): GetUserUsecase.User? {
        val user = UsersStorage.getUser(id) ?: return null

        return GetUserUsecase.User(
            id = user.id,
            accessHash = user.accessHash,
            nickname = user.nickname,
            email = user.email,
            emailVerified = user.emailVerified.takeIf { user.email != null }
        )
    }
}

fun mockGetUserUsecase(): GetUserUsecase = GetUserUsecase(StorageIntegration)
