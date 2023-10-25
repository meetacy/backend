package app.meetacy.backend.types.serializable.place

import app.meetacy.backend.types.place.Place
import app.meetacy.backend.types.serializable.address.serializable
import app.meetacy.backend.types.serializable.address.type
import app.meetacy.backend.types.serializable.location.serializable
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.place.Place as PlaceSerializable

fun PlaceSerializable.type() = Place(address.type(), location.type())
fun Place.serializable() = PlaceSerializable(address.serializable(), location.serializable())
