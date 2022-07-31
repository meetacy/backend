package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.users.CreateUserUsecase

private object CreateUserStorageIntegration : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserId =
        UsersStorage.addUser(accessHash, nickname).id
}

fun mockCreateUserUsecase(): CreateUserUsecase = CreateUserUsecase(
    MockHashGeneratorIntegration,
    CreateUserStorageIntegration
)
