
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.sdk.exception.MeetacyInternalException
import app.meetacy.sdk.types.amount.amount
import app.meetacy.sdk.types.datetime.DateTime
import app.meetacy.sdk.types.paging.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TestInvitations {

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

    @Test
    fun `test invitation acceptation`() = runTestServer {
        val invitor = generateTestAccount()
        val invited = generateTestAccount()
        val meeting = invitor.meetings.createTestMeeting()
        invited.friends.add(invitor.id)
        val invitation = invitor.invitations.create(
            invitor.users.get(invited.id),
            DateTime.parse("2080-06-05T18:00:00Z"),
            meeting.id
        )
        invited.invitations.accept(invitation.id)
        val participants = meeting.participants.paging(10.amount).asFlow().toList().flatten()

        assertContains(participants.map { it.data.id }, invited.id)

        val exception = assertThrows<MeetacyInternalException> {
            runBlocking {
                invited.invitations.accept(invitation.id)
            }
        }
        assertEquals(Failure.InvitationNotFound.errorMessage, exception.message)
    }
}
