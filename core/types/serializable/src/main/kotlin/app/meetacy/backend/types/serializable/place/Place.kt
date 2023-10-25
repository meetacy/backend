package app.meetacy.backend.types.serializable.place

import app.meetacy.backend.types.serializable.address.Address
import app.meetacy.backend.types.serializable.location.Location
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val address: Address,
    val location: Location
)
