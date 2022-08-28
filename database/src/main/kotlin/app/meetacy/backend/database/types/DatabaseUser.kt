package app.meetacy.backend.database.types

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId

class DatabaseUser (
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false
)
