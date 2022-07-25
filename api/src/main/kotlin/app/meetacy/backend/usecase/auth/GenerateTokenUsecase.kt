package app.meetacy.backend.usecase.auth

class GenerateTokenUsecase(private val storage: Storage, private val tokenGenerator: TokenGenerator) {

    suspend fun generateToken(nickname: String): String {
        val newUserId = storage.createUser(nickname)
        val token = tokenGenerator.generateToken()
        storage.addToken(newUserId, token)
        return token
    }


    interface Storage {
        suspend fun createUser(nickname: String): Long
        suspend fun addToken(id: Long, token: String)
    }

    interface TokenGenerator {
        fun generateToken(): String
    }
}
