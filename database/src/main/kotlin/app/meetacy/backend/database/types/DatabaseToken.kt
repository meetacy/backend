package app.meetacy.backend.database.types

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserId

class DatabaseToken(
    val ownerId: UserId,
    val value: AccessIdentity
)
