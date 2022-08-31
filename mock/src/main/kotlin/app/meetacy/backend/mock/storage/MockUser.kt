package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.UserIdentity

data class MockUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false
)
