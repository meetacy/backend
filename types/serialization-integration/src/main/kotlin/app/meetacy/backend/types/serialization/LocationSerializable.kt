package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Location
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class LocationSerializable(
    private val latitude: Double,
    private val longitude: Double
) {
    fun type() = Location(latitude, longitude)
}

fun Location.serializable() = LocationSerializable(latitude, longitude)
