import app.meetacy.sdk.types.location.Location
import kotlin.test.Test

class TestSearch {
    @Test
    fun `test search`() = runTestServer {
        val self = generateTestAccount()
        self.meetings.createTestMeeting("test")
        self.api.search(Location.NullIsland, "test")
    }
}
