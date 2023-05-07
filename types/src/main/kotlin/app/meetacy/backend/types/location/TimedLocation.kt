package app.meetacy.backend.types.location

import app.meetacy.backend.types.datetime.DateTime

data class TimedLocation(
    val location: Location,
    val capturedAt: DateTime
) {
    constructor(
        latitude: Double,
        longitude: Double,
        capturedAt: DateTime
    ) : this(
        location = Location(latitude, longitude),
        capturedAt = capturedAt
    )

    val latitude: Double get() = location.latitude
    val longitude: Double get() = location.longitude
}
