package app.meetacy.backend.feature.invitations.database.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId


class DatabaseInvitation(
    val id: InvitationId,
    val invitedUserId: UserId,
    val inviterUserId: UserId,
    val meetingId: MeetingId,
    val isAccepted: Boolean?
)
