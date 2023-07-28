package app.meetacy.backend.types.serializable.location

import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.serializable.location.Location as LocationSerializable

fun LocationSerializable.type() = Location(latitude, longitude)
fun Location.serializable() = LocationSerializable(latitude, longitude)
