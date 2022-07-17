package app.meetacy.backend.mock.storage

object UsersStorage {
    private val data: MutableMap<Long, MockUser> = mutableMapOf()

    fun addUser(accessHash: String, nickname: String): MockUser {
        val id = data.size.toLong()
        val user = MockUser(id, accessHash, nickname)
        data[id] = user
        return user
    }

    fun getUser(id: Long) = data[id]

    fun isEmailOccupied(email: String) = data.values.any { it.email == email && it.emailVerified }

    fun updateEmail(userId: Long, email: String) {
        val user = data[userId] ?: return
        data[userId] = user.copy(email = email)
    }

    fun verifyEmail(id: Long) {
        val user = data[id] ?: return
        data[id] = user.copy(emailVerified = true)
    }
}
