package app.meetacy.backend.database.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId


class DatabaseInvitation(
    val id: InvitationId,
    val invitedUserId: UserId,
    val inviterUserId: UserId,
    val meetingId: MeetingId,
    val isAccepted: Boolean?
)
