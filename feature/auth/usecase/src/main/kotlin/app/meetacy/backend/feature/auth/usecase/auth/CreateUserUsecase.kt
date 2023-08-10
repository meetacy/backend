package app.meetacy.backend.feature.auth.usecase.auth

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.user.UserId

class CreateUserUsecase(
    private val generator: AccessHashGenerator,
    private val storage: app.meetacy.backend.feature.auth.usecase.auth.CreateUserUsecase.Storage
) {
    suspend fun createUser(nickname: String): UserId {
        val accessHash = AccessHash(generator.generate())
        return storage.addUser(accessHash, nickname)
    }

    interface Storage {
        suspend fun addUser(accessHash: AccessHash, nickname: String): UserId
    }
}
