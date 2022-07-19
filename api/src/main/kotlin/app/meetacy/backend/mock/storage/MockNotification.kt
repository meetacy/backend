package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId

data class MockNotification(
    val id: Long,
    val ownerId: UserId,
    val type: Type,
    val subscriberId: UserId? = null,
    val invitedMeetingId: MeetingId? = null
) {
    enum class Type {
        Subscription, Invitation
    }
}
