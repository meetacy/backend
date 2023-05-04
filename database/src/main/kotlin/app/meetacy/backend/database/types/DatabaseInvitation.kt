package app.meetacy.backend.database.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserIdentity


class DatabaseInvitation(
    val id: InvitationIdentity,
    val expiryDate: Date,
    val invitedUserId: UserIdentity,
    val invitorUserId: UserIdentity,
    val meeting: MeetingIdentity,
    val title: String,
    val description: String
)