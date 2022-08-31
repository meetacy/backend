package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId

data class MockToken(
    val ownerId: UserId,
    val value: AccessToken
)
