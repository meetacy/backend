package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.domain.UserId

data class MockNotification(
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
