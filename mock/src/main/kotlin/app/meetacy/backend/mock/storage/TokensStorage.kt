package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity

object TokensStorage {
    private val data: MutableList<MockToken> = mutableListOf()

    fun addToken(
        userIdentity: UserId,
        token: AccessToken
    ) {
        data += MockToken(
            ownerId = userIdentity,
            value = token
        )
    }

    fun getToken(token: AccessToken): MockToken? = data.firstOrNull { token == it.value }
}
