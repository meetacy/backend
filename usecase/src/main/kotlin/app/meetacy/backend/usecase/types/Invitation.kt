package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId

data class Invitation(
    val identity: InvitationIdentity,
    val expiryDate: DateTime,
    val invitedUserId: UserId,
    val invitorUserId: UserId,
    val meeting: MeetingId,
    val title: String,
    val description: String
) {
    val id = identity.id
}
