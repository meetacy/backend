package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.UserId

data class MockUser(
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false
)
