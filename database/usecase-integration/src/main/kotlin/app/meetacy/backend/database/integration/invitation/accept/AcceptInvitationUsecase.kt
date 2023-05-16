package app.meetacy.backend.database.integration.invitation.accept

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase.Storage
import org.jetbrains.exposed.sql.Database

class DatabaseAcceptInvitationStorage(db: Database): Storage {
    private val invitationsTable = InvitationsTable(db)
    private val participantsTable = ParticipantsTable(db)
    private val meetingsTable = MeetingsTable(db)

    override suspend fun isInvited(userId: UserId, invitationId: InvitationId): Boolean {
        with(invitationsTable) {
            getInvitationsByInvitationIds(
                userId,
                listOf(invitationId)
            ).singleOrNull() ?: return false

            return true
        }
    }

    /**
     * @return true if user is successfully added to meeting, false otherwise
     */
    override suspend fun addToMeetingByInvitation(userId: UserId, invitationId: InvitationId): Boolean {
        val invitation = invitationsTable.getInvitationsByInvitationIds(
            userId,
            listOf(invitationId)
        ).singleOrNull() ?: return false

        participantsTable.addParticipant(
            participantId = userId,
            meetingId = invitation.meeting
        )
        return invitationsTable.markAsAccepted(userId, invitationId)
    }

    /**
     * @return true if invitation is expired, false otherwise
     */
    override suspend fun isExpired(id: InvitationId): Boolean {
        val invitation = invitationsTable.getInvitationsByInvitationIds(listOf(id)).singleOrNull() ?: return true
        return invitation.expiryDate < DateTime.now() || invitation.isAccepted == true
    }

    override suspend fun doesMeetingExist(id: InvitationId): Boolean {
        return meetingsTable
            .getMeetingOrNull(
                id = invitationsTable.getInvitationsByInvitationIds(listOf(id))
                    .singleOrNull()
                    ?.meeting ?: return false
            ) != null
    }
}