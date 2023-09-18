package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.serializable.datetime.DateTime
import app.meetacy.backend.types.serializable.location.Location
import kotlinx.serialization.Serializable

@Serializable
data class UserLocationSnapshot(
    val user: User,
    val location: Location,
    val capturedAt: DateTime
)
