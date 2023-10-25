package app.meetacy.google.maps

data class GooglePlace(
    val id: String,
    val address: Address,
    val location: Location,
) {
    data class Address(
        val country: String,
        val city: String,
        val street: String,
        val placeName: String?,
    )
    data class Location(
        val latitude: Double,
        val longitude: Double
    )
}
