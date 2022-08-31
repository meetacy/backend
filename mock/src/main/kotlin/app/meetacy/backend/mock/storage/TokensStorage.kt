package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId

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

    fun getToken(token: AccessIdentity): MockToken? =
        data.firstOrNull { token.accessToken == it.value }
}
