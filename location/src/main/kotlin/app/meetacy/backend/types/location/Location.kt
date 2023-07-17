package app.meetacy.backend.types.location

import app.meetacy.backend.types.meters.Kilometers
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Location(
    val latitude: Double,
    val longitude: Double
) {
    fun measureDistance(other: Location): Kilometers {
        val earthRadiusKilometers = 6371.0

        val dLat = Math.toRadians(latitude - other.latitude)
        val dLon = Math.toRadians(longitude - other.longitude)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(other.latitude)) * cos(Math.toRadians(latitude)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return Kilometers(double = earthRadiusKilometers * c)
    }
}
