package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId

data class Invitation(
    val id: String,
    val expiryDate: Date,
    val invitedUserId: UserId,
    val invitorUserId: UserId,
    val meeting: MeetingId,
    val title: String,
    val description: String
) {
}