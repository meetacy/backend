package app.meetacy.backend.mock.storage

data class MockConfirmHash(
    val ownerId: Long,
    val email: String,
    val confirmHash: String
)
