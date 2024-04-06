
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.sdk.exception.MeetacyInternalException
import app.meetacy.sdk.invitations.AuthorizedInvitationRepository
import app.meetacy.sdk.types.amount.amount
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
        invited.friends.add(invitor.id)

        val actual = invitor.invitations.create(
            usersIds = listOf(invited.id),
            meetingId = meeting.id
        ).first()

        assertEquals(invited.id, actual.invitedUser.id)
        assertEquals(invitor.id, actual.invitorUser.id)
        assertEquals(meeting.id, actual.meeting.id)
    }

    @Test
    fun `test invitations creation`() = runTestServer {
        val invitor = generateTestAccount()

        val firstInvited = generateTestAccount(postfix = "First")
        val secondInvited = generateTestAccount(postfix = "Second")

        val meeting = invitor.meetings.createTestMeeting()
        firstInvited.friends.add(invitor.id)
        secondInvited.friends.add(invitor.id)

        val actual = invitor.invitations.create(
            usersIds = listOf(firstInvited.id, secondInvited.id),
            meetingId = meeting.id
        )

        val firstActual = actual[0]
        val secondActual = actual[1]

        assertEquals(firstInvited.id, firstActual.invitedUser.id)
        assertEquals(invitor.id, firstActual.invitorUser.id)
        assertEquals(meeting.id, firstActual.meeting.id)

        assertEquals(secondInvited.id, secondActual.invitedUser.id)
        assertEquals(invitor.id, secondActual.invitorUser.id)
        assertEquals(meeting.id, secondActual.meeting.id)
    }

    @Test
    fun `test invitation acceptation`() = runTestServer {
        val invitor = generateTestAccount()
        val invited = generateTestAccount()
        val meeting = invitor.meetings.createTestMeeting()
        invited.friends.add(invitor.id)
        val invitation = invitor.invitations.create(
            listOf(invited.id) ,
            meeting.id
        ).first()
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

    @Test
    fun `test invitation denying`() = runTestServer {
        val invitor = generateTestAccount()
        val invited = generateTestAccount()
        val alice = generateTestAccount()
        val meeting = invitor.meetings.createTestMeeting()
        invited.friends.add(invitor.id)

        val invitation = invitor.invitations.create(
            listOf(invited.id),
            meeting.id
        ).first()

        try {
            alice.invitations.deny(invitation.id)
        } catch (e: Throwable) {
            assert(e is MeetacyInternalException)
            assert(e.message == Failure.InvitationNotFound.errorMessage)
            println("Alice failed to deny invitation, everything is OK now")
        }

        try {
            invited.invitations.deny(invitation.id)
            println("Invited user successfully denied the invitation")
        } catch (e: Throwable) {
            println("Invited user failed to deny invitation")
            throw e
        }
        assert(!meeting.participants.paging(10.amount).asFlow().toList().flatten()
            .map { it.data.id }.contains(invited.id))
    }

    @Test
    fun `test invitation cancellation`() = runTestServer {
        val inviter = generateTestAccount()
        val invited = generateTestAccount()
        val alice = generateTestAccount()

        invited.friends.add(inviter.id)
        val meeting = inviter.meetings.createTestMeeting()
        val invitation = inviter.invitations.create(listOf(invited.id), meeting.id).first()

        var aliceThrowable: Throwable? = null
        try {
            alice.invitations.cancel(invitation.id)
        } catch (t: Throwable) {
            aliceThrowable = t
        }
        assert(aliceThrowable is MeetacyInternalException)
        assert(aliceThrowable?.message == Failure.InvitationNotFound.errorMessage)
        println("Alice failed to cancel invitation, everything is OK now")

        try {
            inviter.invitations.cancel(invitation.id)
            println("Invitor successfully denied the invitation")
        } catch (e: Throwable) {
            println("Invitor failed to cancel invitation")
            throw e
        }
        assert(!meeting.participants.paging(10.amount).asFlow().toList().flatten()
            .map { it.data.id }.contains(invited.id))
    }
}
