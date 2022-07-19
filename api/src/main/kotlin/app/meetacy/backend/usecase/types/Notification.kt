package app.meetacy.backend.usecase.types

import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.serialization.DateSerializer
import app.meetacy.backend.serialization.NotificationIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

sealed interface Notification {
    val id: NotificationId
    val isNew: Boolean

    class Subscription(
        override val isNew: Boolean,
        override val id: NotificationId,
        val subscriber: User,
        val date: Date
    ) : Notification

    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        val meeting: Meeting,
        val date: Date
    ) : Notification
}
