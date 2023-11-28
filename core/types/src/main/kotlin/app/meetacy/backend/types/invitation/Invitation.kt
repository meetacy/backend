package app.meetacy.backend.types.invitation

import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView

data class FullInvitation(
    val id: InvitationId,
    val invitedUserId: UserId,
    val inviterUserId: UserId,
    val meetingId: MeetingId,
    val isAccepted: Boolean?
)

data class InvitationView(
    val id: InvitationId,
    val invitedUser: UserView,
    val inviterUser: UserView,
    val meeting: MeetingView,
    val isAccepted: Boolean?
)
