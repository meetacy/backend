package app.meetacy.backend.database.types

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity

class DatabaseToken(
    val ownerId: UserId,
    val value: AccessToken
)
