package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.UserId

object UsersStorage {
    private val data: MutableMap<UserId, MockUser> = mutableMapOf()

    fun addUser(accessHash: AccessHash, nickname: String): MockUser {
        val id = UserId(data.size.toLong())
        val user = MockUser(id, accessHash, nickname)
        data[id] = user
        return user
    }

    fun getUser(id: UserId) = data[id]

    fun isEmailOccupied(email: String) = data.values.any { it.email == email && it.emailVerified }

    fun updateEmail(userId: UserId, email: String) {
        val user = data[userId] ?: return
        data[userId] = user.copy(email = email)
    }

    fun verifyEmail(id: UserId) {
        val user = data[id] ?: return
        data[id] = user.copy(emailVerified = true)
    }
}
