package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.NotificationId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
@JvmInline
value class NotificationIdSerializable(private val long: Long) {
    fun type() = NotificationId(long)
}

fun NotificationId.serializable() = NotificationIdSerializable(long)
