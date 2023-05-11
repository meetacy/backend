package app.meetacy.backend.types.location

import app.meetacy.backend.types.datetime.DateTime

data class LocationSnapshot(
    val latitude: Double,
    val longitude: Double,
    val capturedAt: DateTime
) {
    constructor(
        location: Location,
        capturedAt: DateTime
    ) : this(
        latitude = location.latitude,
        longitude = location.longitude,
        capturedAt = capturedAt
    )

    val location: Location get() = Location(latitude, longitude)
}
