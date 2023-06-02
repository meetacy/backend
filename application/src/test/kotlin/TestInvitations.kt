
import app.meetacy.sdk.exception.MeetacyInternalException
import app.meetacy.sdk.types.datetime.DateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class TestInvitations {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invitation creation`() = runTestServer {
        val invitor = generateTestAccount()
        val invited = generateTestAccount()
        val meeting = invitor.meetings.createTestMeeting()

        assertThrows<MeetacyInternalException> {
            runBlocking {
                invitor.invitations.create(
                    invitor.users.get(invited.id),
                    DateTime.parse("2080-06-05T18:00:00Z"),
                    meeting.id
                )
            }
        }
        invited.friends.add(invitor.id)
        val invitation = invitor.invitations.create(
            invitor.users.get(invited.id),
            DateTime.parse("2080-06-05T18:00:00Z"),
            meeting.id
        )
        assertEquals(invitation.meeting.id, meeting.id)
    }
}