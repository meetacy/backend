package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId

data class MockToken(
    val ownerId: UserId,
    val value: AccessToken
)
