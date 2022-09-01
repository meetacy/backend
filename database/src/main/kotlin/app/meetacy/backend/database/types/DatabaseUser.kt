package app.meetacy.backend.database.types

import app.meetacy.backend.types.UserIdentity

class DatabaseUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false
)
