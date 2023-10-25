package app.meetacy.google.maps

interface GooglePlacesTextSearch {
    suspend fun search(
        location: GooglePlace.Location,
        query: String
    ): List<GooglePlace>

    object NoOp : GooglePlacesTextSearch {
        override suspend fun search(location: GooglePlace.Location, query: String): List<GooglePlace> = emptyList()
    }
}
