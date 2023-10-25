package app.meetacy.google.maps.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GooglePlacesSingleResponse(
    @SerialName("status")
    val status: String,
    @SerialName("result")
    val result: GooglePlacesResult? = null
)
