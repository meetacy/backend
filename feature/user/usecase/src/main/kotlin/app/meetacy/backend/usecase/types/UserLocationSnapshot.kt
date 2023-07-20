package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot

data class UserLocationSnapshot(
    val user: UserView,
    val location: Location,
    val capturedAt: DateTime
) {
    constructor(user: UserView, location: LocationSnapshot) : this(
        user = user,
        location = location.location,
        capturedAt = location.capturedAt
    )

    val locationSnapshot: LocationSnapshot get() = LocationSnapshot(
        location = location,
        capturedAt = capturedAt
    )
}
