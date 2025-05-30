package app.meetacy.backend.feature.auth.usecase

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.users.UserId

class CreateUserUsecase(
    private val generator: AccessHashGenerator,
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
