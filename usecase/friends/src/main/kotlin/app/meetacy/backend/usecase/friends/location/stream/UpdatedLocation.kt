package app.meetacy.backend.usecase.friends.location.stream

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location

data class UpdatedLocation(
    val location: Location,
    val updatedAt: DateTime
)
