
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.sdk.exception.MeetacyInternalException
import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.datetime.Date
import app.meetacy.sdk.types.datetime.meetacyDate
import app.meetacy.sdk.types.location.Location
import app.meetacy.sdk.types.meeting.Meeting
import app.meetacy.sdk.types.meeting.MeetingId
import app.meetacy.sdk.types.optional.Optional
import app.meetacy.sdk.types.paging.asFlow
import app.meetacy.sdk.types.paging.data
import kotlinx.coroutines.flow.toList
import java.time.Duration
import java.time.Instant
import kotlin.test.Test

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
            .paging(chunkSize = 2.amount)
            .asFlow()
            .toList()
            .flatten()

        require(meetingsList.size == meetingsAmount) {
            "Meetings size returned by first pagination differs from actual size"
        }

        val emptySecondList = secondTestApi
            .meetings.history
            .paging(chunkSize = 2.amount)
            .asFlow()
            .toList()
            .flatten()

        require(emptySecondList.isEmpty()) { "Should be empty before participating" }

        meetings.forEach { meeting ->
            secondTestApi.meetings.participate(meeting.id)
        }

        val secondMeetingsList = secondTestApi
            .meetings.history
            .paging(chunkSize = 2.amount)
            .asFlow()
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

        val secondAccount = generateTestAccount(postfix = "#2")

        val secondMeetings = self.meetings.map.list(Location.NullIsland)
        require(secondMeetings.isEmpty())

        self.meetings.create(
            title = "Test Meeting #1",
            date = Instant.now().minus(Duration.ofDays(2)).meetacyDate,
            location = Location.NullIsland
        )

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

    @Test
    fun `get meeting test`() = runTestServer {
        val self = generateTestAccount()
        val other = generateTestAccount()

        val selfMeeting = self.meetings.create(
            title = "Alex Sokol",
            date = Date.today(),
            location = Location.NullIsland
        ).data

        val selfCheck = self.meetings.get(selfMeeting.id).data
        require(selfCheck == selfMeeting)
        val otherCheck = other.meetings.get(selfMeeting.id).data
        require(otherCheck.id == selfMeeting.id)

        val (id) = selfMeeting.id.string.split(':')

        // self not allowed check

        val selfException = try {
            self.meetings.get(
                MeetingId(
                    "$id:0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                )
            )
            null
        } catch (exception: MeetacyInternalException) {
            exception
        }
        require(selfException is MeetacyInternalException)

        val otherException = try {
            other.meetings.get(
                MeetingId(
                    "$id:0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                )
            )
            null
        } catch (exception: MeetacyInternalException) {
            exception
        }
        require(otherException is MeetacyInternalException)
    }

    @Test
    fun `edit meeting test`() = runTestServer {
        val client = generateTestAccount()

        val meeting = client.meetings.create(
            title = "Test Meeting",
            date = Date.today(),
            location = Location.NullIsland
        )

        val newTitle = HashGenerator.generate((1..100).random())

        val edited = meeting.edited(title = Optional.Present(newTitle))

        require(edited.title == newTitle)

        val updated = meeting.updated()

        require(updated.title == newTitle)
    }

    @Test
    fun `test meeting with invalid identity`() = runTestServer {
        val client = generateTestAccount()

        val (meetingId) = client.meetings.createTestMeeting().id.string.split(":")

        val crackedMeetingId = MeetingId(InvalidId(meetingId))

        val exception = try {
            client.meetings.get(crackedMeetingId)
            null
        } catch (exception: MeetacyInternalException) {
            exception
        }

        require(exception is MeetacyInternalException)
    }

    @Test
    fun `test participants list`() = runTestServer {
        val participantsCount = (0..8).random() // fixme:
        // infinitely sends requests to a participants' list when count is more than 8

        println("Test with participants count: $participantsCount")

        val client = generateTestAccount()

        val participants = List(participantsCount) { i ->
            generateTestAccount(postfix = " participant #$i")
        }

        val meeting = client.meetings.createTestMeeting()

        val emptyParticipants = meeting.participants.paging(10.amount).asFlow().toList().flatten()

        require(emptyParticipants.size == 1)

        for (participant in participants) {
            meeting.base.participate(
                token = participant.token
            )
        }

        val actualParticipants = meeting.participants.paging(10.amount).asFlow().toList().flatten()

        require(actualParticipants.size == participantsCount + 1)
    }

    @Test
    fun `test active meetings`() = runTestServer {
        val user1 = generateTestAccount()
        val user2 = generateTestAccount()
        val meeting = user1.meetings.createTestMeeting()
        user2.meetings.participate(meeting.id)

        assert(user2.meetings.history.active(10.amount).data.all { it.data.id == meeting.id })
    }

    @Test
    fun `test meeting with invalid title length`() = runTestServer {
        val self = generateTestAccount()
        val invalidTitle = buildString {
            repeat(44) {
                append("ass")
            }
        }

        val exception = try {
            self.meetings.create(invalidTitle, Date.today(), Location.NullIsland, "TestInvalidTitle")
            null
        } catch (exception: MeetacyInternalException) {
            exception
        }

        require(exception is MeetacyInternalException)
    }

    @Test
    fun `test meeting with invalid description length`() = runTestServer {
        val self = generateTestAccount()
        val invalidDescription = buildString {
            repeat(200) {
                append("ass")
            }
        }

        val exception = try {
            self.meetings.create("titleky", Date.today(), Location.NullIsland, invalidDescription)
            null
        } catch (exception: MeetacyInternalException) {
            exception
        }

        require(exception is MeetacyInternalException)
    }
    
    fun `test leave meeting`() = runTestServer {
        val user = generateTestAccount()
        val participant = generateTestAccount("participant")

        val meeting = user.meetings.createTestMeeting("Test Leave")
        participant.meetings.participate(meeting.id)
        val participantsListBeforeQuit = meeting.participants.paging(10.amount).asFlow().toList().flatten()
        require(participantsListBeforeQuit.size == 2)

        participant.meetings.leave(meeting.id)
        val participantsListAfterQuit = meeting.participants.paging(10.amount).asFlow().toList().flatten()
        require(participantsListAfterQuit.size == 1)
    }
}
