package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.serialization
import io.ktor.http.*
import io.ktor.server.application.*

fun Parameters.latitudeOrNull(name: String = "latitude"): Double? = serialization { this[name]?.toDouble() }
fun Parameters.longitudeOrNull(name: String = "longitude"): Double? = serialization { this[name]?.toDouble() }
