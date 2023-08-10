package app.meetacy.backend.feature.auth.usecase.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId

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
