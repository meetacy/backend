package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId

data class FullInvitation(
    val identity: InvitationIdentity,
    val expiryDate: DateTime,
    val invitedUserId: UserId,
    val invitorUserId: UserId,
    val meeting: MeetingId,
    val isAccepted: Boolean?
) {
    val id = identity.id
}

data class InvitationView(
    val identity: InvitationIdentity,
    val expiryDate: DateTime,
    val invitedUserView: UserView,
    val invitorUserView: UserView,
    val meetingView: MeetingView,
    val invitorIsSelf: Boolean,
    val invitedIsSelf: Boolean,
    val isAccepted: Boolean?
) {
    val id = identity.id
}
