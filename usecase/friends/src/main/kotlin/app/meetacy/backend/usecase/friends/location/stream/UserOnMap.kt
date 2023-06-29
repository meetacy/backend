package app.meetacy.backend.usecase.friends.location.stream

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.usecase.types.UserView

data class UserOnMap(
    val user: UserView,
    val location: Location,
    val updatedAt: DateTime
) {
    constructor(user: UserView, updatedLocation: UpdatedLocation) : this(
        user = user,
        location = updatedLocation.location,
        updatedAt = updatedLocation.updatedAt
    )
}
