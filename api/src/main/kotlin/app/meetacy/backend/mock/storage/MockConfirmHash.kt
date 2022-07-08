package app.meetacy.backend.mock.storage

data class MockConfirmHash(
    val userId: Int,
    val email: String,
    val confirmHash: String
)
