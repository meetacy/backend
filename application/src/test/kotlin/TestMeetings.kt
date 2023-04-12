import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.datetime.meetacyDate
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.meeting.Meeting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class TestMeetings {

    @Test
    fun `pagination test of meetings`() = runTestServer {
        val meetingsAmount = (0..20).random()

        println("Testing with meetingsAmount: $meetingsAmount")

        val testApi = generateTestAccount()
        val secondTestApi = generateTestAccount(postfix = "Second")

        val meetings = List(meetingsAmount) { i ->
            testApi.meetings.create(
                title = "Test Meeting #${i + 1}",
                date = Date.today(),
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
    fun `test of meetings on map`() = runTestServer {

        val self = generateTestAccount()

        val firstMeetings = self.meetings.map.list(Location.NullIsland)
        require(firstMeetings.isEmpty()) { "Account was just created, there is no meetings on map" }

        self.meetings.create(
            title = "Test Meeting #1",
            date = Instant.now().minus(Duration.ofDays(2)).meetacyDate,
            location = Location.NullIsland
        )

        val secondMeetings = self.meetings.map.list(Location.NullIsland)
        require(secondMeetings.isEmpty())

        self.meetings.create(
            title = "Test Meeting #2",
            date = Date.today(),
            location = Location.NullIsland
        )

        self.meetings.create(
            title = "Test Meeting #3",
            date = Instant.now().plus(Duration.ofDays(1)).meetacyDate,
            location = Location.NullIsland
        )

        val secondAccount = generateTestAccount(postfix = "#2")

        val meeting = secondAccount.meetings.create(
            title = "Test Meeting #4",
            date = Date.today(),
            location = Location.NullIsland
        )
        meeting.base.participate(self.token)

        val thirdMeetings = self.meetings.map.list(Location.NullIsland)
        require(thirdMeetings.size == 3)
    }

    @Test
    fun `test public meetings`() = runTestServer {

        val first = generateTestAccount(postfix = "#1")
        val firstLocation = Location.NorthPole

        val second = generateTestAccount(postfix = "#2")
        val secondLocation = Location.NullIsland

        val third = generateTestAccount(postfix = "#3")
        val thirdLocation = Location.NullIsland

        first.meetings.create(
            title = "Тусовка на серверном полюсе",
            date = Date.today(),
            location = firstLocation,
            visibility = Meeting.Visibility.Public
        )


        require(first.meetings.map.list(firstLocation).size == 1)
        require(second.meetings.map.list(secondLocation).isEmpty())
        require(third.meetings.map.list(thirdLocation).isEmpty())

        second.meetings.create(
            title = "Тусовка на системе типов Java",
            date = Date.today(),
            location = secondLocation,
            visibility = Meeting.Visibility.Public
        )

        require(second.meetings.map.list(secondLocation).size == 1)
        require(third.meetings.map.list(thirdLocation).size == 1)

        // Check that nothing happened to the first
        require(first.meetings.map.list(firstLocation).size == 1)
    }
}
