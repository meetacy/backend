package app.meetacy.backend.usecase.users

class CreateUserUsecase(
    private val generator: HashGenerator,
    private val storage: Storage
) {
    suspend fun createUser(nickname: String): Long {
        val accessHash = generator.generateHash()
        return storage.addUser(accessHash, nickname)
    }
    interface Storage {
        suspend fun addUser(accessHash: String, nickname: String): Long
    }
    interface HashGenerator {
        fun generateHash(): String
    }
}
