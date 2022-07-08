package app.meetacy.backend.mock.storage

object UsersStorage {
    private val data: MutableMap<Int, MockUser> = mutableMapOf()

    fun addUser(id: Int, nickname: String) {
        data[id] = MockUser(id, nickname)
    }

    fun getUser(id: Int) = data[id]

    fun updateEmail(id: Int, email: String) {
        val user = data[id] ?: return
        data[id] = user.copy(email = email)
    }

    fun verifyEmail(id: Int) {
        val user = data[id] ?: return
        data[id] = user.copy(emailVerified = true)
    }
}
