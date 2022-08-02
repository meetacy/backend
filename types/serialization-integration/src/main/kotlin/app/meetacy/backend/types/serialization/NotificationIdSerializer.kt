package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.NotificationId
import kotlinx.serialization.Serializer

@Serializer(NotificationId::class)
object NotificationIdSerializer
