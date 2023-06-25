package app.meetacy.backend.database.types

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId

class DatabaseToken(
    val ownerId: UserId,
    val value: AccessIdentity
)
