package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.usecase.types.UserView

data class UserOnMap(
    val user: UserView,
    val location: Location,
    val capturedAt: DateTime
) {
    constructor(user: UserView, location: TimedLocation) : this(
        user = user,
        location = location.location,
        capturedAt = location.capturedAt
    )

    val timedLocation: TimedLocation get() = TimedLocation(
        location = location,
        capturedAt = capturedAt
    )
}
