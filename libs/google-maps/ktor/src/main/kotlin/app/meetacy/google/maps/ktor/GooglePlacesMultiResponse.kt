package app.meetacy.google.maps.ktor

import app.meetacy.google.maps.GooglePlace
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GooglePlacesMultiResponse(
    @SerialName("status")
    val status: String,
    @SerialName("results")
    val results: List<GooglePlacesResult>
) {
    fun map(): List<GooglePlace> = results.mapNotNull { result -> result.mapOrNull() }
}
