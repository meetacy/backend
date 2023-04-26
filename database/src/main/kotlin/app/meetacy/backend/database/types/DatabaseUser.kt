package app.meetacy.backend.database.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.types.user.Username

class DatabaseUser(
    val identity: UserIdentity,
    val nickname: String,
    val username: Username? = null,
    val email: String? = null,
    val emailVerified: Boolean = false,
    val avatarId: FileId? = null
)
