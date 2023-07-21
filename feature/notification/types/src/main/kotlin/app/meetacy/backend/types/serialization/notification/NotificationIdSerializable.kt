package app.meetacy.backend.types.serialization.notification

import app.meetacy.backend.types.notification.NotificationId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NotificationIdSerializable(private val string: String) {
    fun type() = NotificationId(string.toLong())
}

fun NotificationId.serializable() = NotificationIdSerializable(long.toString())
