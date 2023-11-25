package app.meetacy.backend.types.serializable.address

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val country: String,
    val city: String,
    val street: String,
    val placeName: String?
)
