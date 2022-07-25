package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.generator.MockHashGenerator
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.users.CreateUserUsecase

private object CreateUserStorageIntegration : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: String, nickname: String): Long =
        UsersStorage.addUser(accessHash, nickname).id
}
private object CreateUserHashGeneratorIntegration : CreateUserUsecase.HashGenerator {
    override fun generateHash(): String =
        MockHashGenerator.generate()
}

fun mockCreateUserUsecase(): CreateUserUsecase = CreateUserUsecase(
    CreateUserHashGeneratorIntegration,
    CreateUserStorageIntegration
)
