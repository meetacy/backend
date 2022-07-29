package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId

object TokensStorage {
    private val data: MutableList<MockToken> = mutableListOf()

    fun addToken(
        userId: UserId,
        token: AccessToken
    ) {
        data += MockToken(
            ownerId = userId,
            value = token
        )
    }

    fun getToken(token: AccessToken): MockToken? = data.firstOrNull { token == it.value }
}
