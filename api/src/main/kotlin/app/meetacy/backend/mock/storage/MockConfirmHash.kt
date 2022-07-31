package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.UserId

data class MockConfirmHash(
    val ownerId: UserId,
    val email: String,
    val confirmHash: String
)
