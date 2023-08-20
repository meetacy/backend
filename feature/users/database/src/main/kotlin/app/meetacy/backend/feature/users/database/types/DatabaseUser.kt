package app.meetacy.backend.feature.users.database.types

import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.users.UserIdentity
import app.meetacy.backend.types.users.Username

class DatabaseUser(
    val identity: UserIdentity,
    val nickname: String,
    val username: Username? = null,
    val email: String? = null,
    val emailVerified: Boolean = false,
    val avatarId: FileId? = null
)
