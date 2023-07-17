package app.meetacy.backend.types.serialization.location

import app.meetacy.backend.types.location.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationSerializable(
    val latitude: Double,
    val longitude: Double
) {
    fun type() = Location(latitude, longitude)
}

fun Location.serializable() = LocationSerializable(latitude, longitude)
