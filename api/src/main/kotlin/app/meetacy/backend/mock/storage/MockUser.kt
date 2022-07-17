package app.meetacy.backend.mock.storage

data class MockUser(
    val id: Long,
    val accessHash: String,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false
)
