package app.meetacy.backend.mock.storage

object TokensStorage {
    private val data: MutableList<MockToken> = mutableListOf()

    fun addToken(
        userId: Long,
        token: String
    ) {
        data += MockToken(
            ownerId = userId,
            value = token
        )
    }

    fun getToken(token: String): MockToken? = data.firstOrNull { token == it.value }
}
