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

    override suspend fun UserId.isInvited(invitationId: InvitationId): Boolean {
        with(invitationsTable) {
            getInvitationsByInvitationIds(
                this@isInvited,
                listOf(invitationId)
            ).singleOrNull() ?: return false

            return true
        }
    }

    /**
     * @return true if user is successfully added to meeting, false otherwise
     */
    override suspend fun UserId.addToMeetingByInvitation(invitationId: InvitationId): Boolean {
        val invitation = invitationsTable.getInvitationsByInvitationIds(
            this,
            listOf(invitationId)
        ).singleOrNull() ?: return false

        with(participantsTable) {
            addParticipant(
                participantId = this@addToMeetingByInvitation,
                meetingId = invitation.meeting
            )
        }
        return invitationsTable.markAsAccepted(this, invitationId)
    }

    /**
     * @return true if invitation is expired, false otherwise
     */
    override suspend fun InvitationId.isExpired(): Boolean {
        val invitation = invitationsTable.getInvitationsByInvitationIds(listOf(this)).singleOrNull() ?: return true
        return invitation.expiryDate < DateTime.now() || invitation.isAccepted == true
    }

    override suspend fun InvitationId.doesMeetingExist(): Boolean {
        return meetingsTable
            .getMeetingOrNull(
                id = invitationsTable.getInvitationsByInvitationIds(listOf(this))
                    .singleOrNull()
                    ?.meeting ?: return false
            ) != null
    }
}