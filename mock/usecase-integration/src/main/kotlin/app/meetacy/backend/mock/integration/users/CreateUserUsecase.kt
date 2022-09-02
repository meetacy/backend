package app.meetacy.backend.mock.integration.users

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.users.CreateUserUsecase

object MockCreateUserStorage : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserId =
        UsersStorage.addUser(accessHash, nickname).identity.userId
}
