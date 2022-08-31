package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity

object UsersStorage {
    private val data: MutableMap<UserId, MockUser> = mutableMapOf()

    fun addUser(accessHash: AccessHash, nickname: String): MockUser {
        val identity = UserIdentity(
            UserId(data.size.toLong()),
            accessHash
        )
        val user = MockUser(identity, nickname)
        data[identity.userId] = user
        return user
    }

    fun getUser(id: UserId) = data[id]

    fun isEmailOccupied(email: String) = data.values.any { it.email == email && it.emailVerified }

    fun updateEmail(userIdentity: UserId, email: String) {
        val user = data[userIdentity] ?: return
        data[userIdentity] = user.copy(email = email)
    }

    fun verifyEmail(id: UserId) {
        val user = data[id] ?: return
        data[id] = user.copy(emailVerified = true)
    }
}
