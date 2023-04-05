import app.meetacy.types.amount.amount
import app.meetacy.types.datetime.DateOrTime
import app.meetacy.types.datetime.meetacyDate
import app.meetacy.types.datetime.meetacyDateTime
import app.meetacy.types.location.Location
import app.meetacy.types.meeting.Meeting
import app.meetacy.types.paging.PagingId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class TestMeetings {

    @Test
    fun `pagination test of meetings`() = runTest {
        val meetingsAmount = (0..20).random()

        startTestEndpoints()

        println("Testing with meetingsAmount: $meetingsAmount")

        val testApi = generateTestAccount()
        val secondTestApi = generateTestAccount(postfix = "Second")

        val meetings = List(meetingsAmount) { i ->
            testApi.meetings.create(
                title = "Test Meeting #${i + 1}",
                date = DateOrTime.Date.today(),
                location = Location.NullIsland
            )
        }

        require(meetings.all { meeting -> meeting.previewParticipants.size == 1 })

        require(meetings.all { meeting -> meeting.isParticipating }) {
            "All created meetings should have 'isParticipating' set to true"
        }

        val meetingsList = testApi
            .meetings.history
            .flow(chunkSize = 2.amount)
            .toList()
            .flatten()
        
        require(meetingsList.size == meetingsAmount) {
            "Meetings size returned by first pagination differs from actual size"
        }

        val emptySecondList = secondTestApi
            .meetings.history
            .flow(chunkSize = 2.amount)
            .toList()
            .flatten()

        require(emptySecondList.isEmpty()) { "Should be empty before participating" }

        meetings.forEach { meeting ->
            secondTestApi.meetings.participate(meeting.id)
        }

        val secondMeetingsList = secondTestApi
            .meetings.history
            .flow(chunkSize = 2.amount)
            .toList()
            .flatten()

        require(secondMeetingsList.all { meeting -> meeting.previewParticipants.size == 2 })

        require(secondMeetingsList.size == meetingsAmount) {
            "Meetings size returned by second pagination differs from actual size"
        }
    }

    @Test
    fun `test of meetings on map`() = runTest {
        val dayMilliseconds = 86_400_000

        startTestEndpoints()

        val self = generateTestAccount()

        val firstMeetings = self.meetings.map.list()
        require(firstMeetings.isEmpty()) { "Account was just created, there is no meetings on map" }

        self.meetings.create(
            title = "Test Meeting #1",
            date = Date(System.currentTimeMillis() - 2 * dayMilliseconds).meetacyDateTime,
            location = Location.NullIsland
        )

        val secondMeetings = self.meetings.map.list()
        require(secondMeetings.isEmpty())

        self.meetings.create(
            title = "Test Meeting #2",
            date = DateOrTime.DateTime.now(),
            location = Location.NullIsland
        )

        self.meetings.create(
            title = "Test Meeting #3",
            date = Date(System.currentTimeMillis() + dayMilliseconds).meetacyDateTime,
            location = Location.NullIsland
        )

        val secondAccount = generateTestAccount(postfix = "#2")

        val meeting = secondAccount.meetings.create(
            title = "Test Meeting #4",
            date = DateOrTime.Date.today(),
            location = Location.NullIsland
        )
        meeting.base.participate(self.token)

        val thirdMeetings = self.meetings.map.list()
        require(thirdMeetings.size == 3)
    }
}
