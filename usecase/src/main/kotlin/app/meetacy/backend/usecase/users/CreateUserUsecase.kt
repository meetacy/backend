package app.meetacy.backend.usecase.users

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.HashGenerator

class CreateUserUsecase(
    private val generator: HashGenerator,
    private val storage: Storage
) {
    suspend fun createUser(nickname: String): UserId {
        val accessHash = AccessHash(generator.generate())
        return storage.addUser(accessHash, nickname)
    }
    interface Storage {
        suspend fun addUser(accessHash: AccessHash, nickname: String): UserId
    }

}