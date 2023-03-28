import app.meetacy.api.MeetacyApi
import app.meetacy.api.production
import app.meetacy.types.amount.amount
import app.meetacy.types.location.Location
import app.meetacy.types.meeting.Meeting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TestMeetings {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `pagination test of meetings`() = runTest {
        val meetingsAmount = (0..20).random()

        startTestEndpoints()

        println("Testing with meetingsAmount: $meetingsAmount")

        val (testApi, _) = generateTestAccount()
        val (secondTestApi, _) = generateTestAccount(postfix = "Second")

        val meetings = List(meetingsAmount) { i ->
            testApi.meetings.create(
                title = "Test Meeting #${i + 1}",
                date = Meeting.DateTimeInfo.Date.today(),
                location = Location.NullIsland
            )
        }

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

        require(secondMeetingsList.size == meetingsAmount) {
            "Meetings size returned by second pagination differs from actual size"
        }
    }
}
