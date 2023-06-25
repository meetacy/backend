package app.meetacy.backend.database.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

data class DatabaseNotification(
    val id: NotificationId,
    val ownerId: UserId,
    val type: Type,
    val date: Date,
    val inviterId: UserId? = null,
    val subscriberId: UserId? = null,
    val invitedMeetingId: MeetingId? = null
) {
    enum class Type {
        Subscription, Invitation
    }
}