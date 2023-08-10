package app.meetacy.backend.types.serializable.notification

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NotificationId(val string: String)