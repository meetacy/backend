package app.meetacy.backend.database.integration.invitations.accept

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase.Storage
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseAcceptInvitationStorage(db: Database): Storage {
    private val invitationsStorage = InvitationsStorage(db)
    private val participantsStorage = ParticipantsStorage(db)
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
        meetingsStorage.getMeetingOrNull(id)?.mapToUsecase()

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
        invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsStorage.isParticipating(meetingId, userId)

    override suspend fun markAsAccepted(id: InvitationId) {
        invitationsStorage.markAsAccepted(id)
    }

    override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) {
        participantsStorage.addParticipant(userId, meetingId)
    }
}
