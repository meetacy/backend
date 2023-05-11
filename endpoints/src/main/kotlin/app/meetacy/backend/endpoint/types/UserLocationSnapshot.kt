package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.location.LocationSerializable
import kotlinx.serialization.Serializable

@Serializable
data class UserLocationSnapshot(
    val user: User,
    val location: LocationSerializable,
    val capturedAt: DateTimeSerializable
)
