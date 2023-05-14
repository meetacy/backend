package app.meetacy.backend.database.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId


class DatabaseInvitation(
    val identity: InvitationIdentity,
    val expiryDate: DateTime,
    val invitedUserId: UserId,
    val invitorUserId: UserId,
    val meeting: MeetingId,
    val title: String,
    val description: String,
    val isAccepted: Boolean?
) {
    val id = identity.id
}