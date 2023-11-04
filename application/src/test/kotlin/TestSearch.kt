import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.search.SearchItem
import kotlin.test.Test

class TestSearch {
    @Test
    fun `test meeting search`() = runTestServer {
        val self = generateTestAccount()
        self.meetings.createTestMeeting("testSearch")
        val result = self.api.search(Location.NullIsland, "testSearch")
        val data = result.first().data
        require(data is SearchItem.Meeting)
        val meeting = data.meeting
        require(meeting.title == "testSearch")
    }
}
