package app.meetacy.backend.serialization

import app.meetacy.backend.domain.NotificationId
import kotlinx.serialization.Serializer

@Serializer(NotificationId::class)
object NotificationIdSerializer
