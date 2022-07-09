package app.meetacy.backend.mock.storage

object UsersStorage {
    private val data: MutableMap<Int, MockUser> = mutableMapOf()

    fun addUser(accessHash: String, nickname: String): MockUser {
        val id = data.size
        val user = MockUser(id, accessHash, nickname)
        data[id] = user
        return user
    }

    fun getUser(id: Int) = data[id]
    fun getUser(email: String) = data.values.firstOrNull { it.email == email }

    fun updateEmail(id: Int, email: String) {
        val user = data[id] ?: return
        data[id] = user.copy(email = email)
    }

    fun verifyEmail(id: Int) {
        val user = data[id] ?: return
        data[id] = user.copy(emailVerified = true)
    }
}
