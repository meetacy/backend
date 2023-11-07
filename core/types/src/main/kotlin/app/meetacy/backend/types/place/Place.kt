package app.meetacy.backend.types.place

import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.location.Location

data class Place(
    val address: Address,
    val location: Location
)
