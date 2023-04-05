package app.meetacy.backend.database.types

import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.user.UserIdentity

class DatabaseUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String? = null,
    val emailVerified: Boolean = false,
    val avatarIdentity: FileIdentity? = null
)
