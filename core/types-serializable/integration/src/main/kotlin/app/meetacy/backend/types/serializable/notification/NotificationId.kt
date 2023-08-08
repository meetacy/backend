package app.meetacy.backend.types.serializable.notification

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.serializable.notification.NotificationId as NotificationIdSerializable

fun NotificationIdSerializable.type() = NotificationId(string.toLong())

fun NotificationId.serializable() = NotificationIdSerializable(long.toString())
