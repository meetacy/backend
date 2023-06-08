package app.meetacy.backend.database.integration.invitation.update

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseUpdateInvitationStorage(db: Database): UpdateInvitationUsecase.Storage {
    private val invitationsStorage = InvitationsStorage(db)
    private val meetingsStorage = MeetingsStorage(db)
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsStorage.isParticipating(meetingId, userId)

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
        invitationsStorage.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun update(
        invitationId: InvitationId,
        expiryDate: DateTime?,
        meetingId: MeetingId?
    ): Boolean =
        invitationsStorage.update(invitationId, expiryDate, meetingId)

    override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
        meetingsStorage.getMeetingOrNull(id)?.mapToUsecase()

}
