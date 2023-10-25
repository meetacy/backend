package app.meetacy.google.maps.ktor

import app.meetacy.google.maps.GooglePlace
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GooglePlacesResult(
    @SerialName("geometry")
    val geometry: Geometry? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("place_id")
    val placeId: String? = null,
    @SerialName("address_components")
    val addressComponents: List<AddressComponent> = emptyList()
) {
    @Serializable
    data class Geometry(
        @SerialName("location")
        val location: Location
    ) {
        @Serializable
        data class Location(
            @SerialName("lat")
            val latitude: Double,
            @SerialName("lng")
            val longitude: Double
        )
    }

    @Serializable
    data class AddressComponent(
        @SerialName("long_name")
        val longName: String,
        @SerialName("short_name")
        val shortName: String,
        @SerialName("types")
        val types: List<String>
    )

    fun getComponent(name: String) = addressComponents.firstOrNull { component -> name in component.types }
    
    fun mapOrNull(): GooglePlace? {
        return GooglePlace(
            id = placeId ?: return null,
            address = GooglePlace.Address(
                country = getComponent("country")
                    ?.longName ?: return null,
                city = (getComponent("locality")
                    ?: getComponent("neighborhood")
                    ?: getComponent("administrative_area_3"))
                    ?.longName ?: return null,
                street =  buildString {
                    val route = getComponent("route")?.longName ?: return null
                    append(route)
                    append(", ")
                    val streetNumber = getComponent("street_number")?.longName ?: return null
                    append(streetNumber)
                },
                placeName = name
            ),
            location = GooglePlace.Location(
                latitude = geometry?.location?.latitude ?: return null,
                longitude = geometry.location.longitude
            )
        )
    }
}
