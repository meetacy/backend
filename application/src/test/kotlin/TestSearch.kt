import app.meetacy.google.maps.GooglePlace
import app.meetacy.google.maps.GooglePlacesTextSearch
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.optional.Optional
import app.meetacy.sdk.types.search.SearchItem
import app.meetacy.sdk.types.user.username
import kotlin.test.Test

class TestSearch {
    @Test
    fun `test meeting search`() = runTestServer {
        val self = generateTestAccount()
        val meeting = self.meetings.createTestMeeting("testSearch")
        val searchResults = self.api.search(Location.NullIsland, "testSearch")
        val searchItem = searchResults.first().data
        require(searchItem is SearchItem.Meeting)
        require(meeting.data == searchItem.meeting)
    }

    @Test
    fun `test user search`() = runTestServer {
        val self = generateTestAccount("testSearch")
        val searchResults = self.api.search(Location.NullIsland, "testSearch")
        val searchItem = searchResults.first().data
        require(searchItem is SearchItem.User)
        require(self.data == searchItem.user)
    }

    @Test
    fun `test that leading at is removed`() = runTestServer {
        val self = generateTestAccount()
        self.edited(
            username = Optional.Present("test_username".username)
        )
        val searchResults = self.api.search(
            location = null,
            prompt = "@test_username"
        )
        val searchItem = searchResults.first().data
        require(searchItem is SearchItem.User)
    }

    @Test
    fun `test place search`() = runTestServer(
        mockGooglePlacesSearch = object : GooglePlacesTextSearch {
            override suspend fun search(location: GooglePlace.Location, query: String): List<GooglePlace> =
                listOf(
                    GooglePlace(
                        id = "0",
                        address = GooglePlace.Address(
                            country = "TEST",
                            city = "TEST",
                            street = "TEST",
                            placeName = "TEST"
                        ),
                        location = GooglePlace.Location(
                            latitude = 0.0,
                            longitude = 0.0
                        )
                    )
                )
        }
    ) {
        val self = generateTestAccount()
        val searchResults = self.api.search(Location.NullIsland, "testSearch")
        val searchItem = searchResults.first().data
        require(searchItem is SearchItem.Place)
        require(searchItem.place.location == Location.NullIsland)
    }
}
