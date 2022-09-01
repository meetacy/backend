package app.meetacy.backend.mock.integration.users

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.users.CreateUserUsecase

object MockCreateUserStorage : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserIdentity =
        UsersStorage.addUser(accessHash, nickname).identity
}
