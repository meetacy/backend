package app.meetacy.backend.database.integration.invitation.accept

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase.Storage
import org.jetbrains.exposed.sql.Database

class DatabaseAcceptInvitationStorage(db: Database): Storage {
    private val invitationsTable = InvitationsTable(db)
    private val participantsTable = ParticipantsTable(db)

    override suspend fun UserId.isInvited(invitationId: InvitationId): Storage.DatabaseResult {
        with(invitationsTable) {
            getInvitationsByInvitationIds(
                this@isInvited,
                listOf(invitationId)
            ).firstOrNull() ?: return Storage.DatabaseResult.NotInvited

            /* else */ return Storage.DatabaseResult.Success
        }
    }

    override suspend fun UserId.addToMeetingByInvitation(invitationId: InvitationId): Storage.DatabaseResult {
        val invitation = invitationsTable.getInvitationsByInvitationIds(
            this,
            listOf(invitationId)
        ).firstOrNull() ?: return Storage.DatabaseResult.NotInvited
        if (invitation.expiryDate < Date.today() || invitation.isAccepted == true) return Storage.DatabaseResult.InvitationExpired
        with(participantsTable) {
            addParticipant(
                participantId = this@addToMeetingByInvitation,
                meetingId = invitation.meeting
            )
        }
        invitationsTable.markAsAccepted(this, invitationId)
        return Storage.DatabaseResult.Success
    }
}