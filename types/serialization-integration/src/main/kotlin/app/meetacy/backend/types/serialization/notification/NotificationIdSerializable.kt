package app.meetacy.backend.types.serialization.notification

import app.meetacy.backend.types.notification.NotificationId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NotificationIdSerializable(private val long: Long) {
    fun type() = NotificationId(long)
}

fun NotificationId.serializable() = NotificationIdSerializable(long)
