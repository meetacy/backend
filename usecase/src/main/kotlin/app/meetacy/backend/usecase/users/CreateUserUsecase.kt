package app.meetacy.backend.usecase.users

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.types.HashGenerator

class CreateUserUsecase(
    private val generator: HashGenerator,
    private val storage: Storage
) {
    suspend fun createUser(nickname: String): UserIdentity {
        val accessHash = AccessHash(generator.generate())
        return storage.addUser(accessHash, nickname)
    }

    interface Storage {
        suspend fun addUser(accessHash: AccessHash, nickname: String): UserIdentity
    }

}
