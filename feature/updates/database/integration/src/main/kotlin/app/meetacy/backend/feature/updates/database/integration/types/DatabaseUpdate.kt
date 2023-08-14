package app.meetacy.backend.feature.updates.database.integration.types

import app.meetacy.backend.feature.updates.database.types.DatabaseUpdate
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.feature.updates.usecase.types.FullUpdate

fun DatabaseUpdate.mapToUsecase(): FullUpdate = when (type) {
    DatabaseUpdate.Type.Notification -> FullUpdate.Notification(
        id = id,
        notificationId = NotificationId(underlyingId)
    )
}

fun FullUpdate.mapToDatabase(): DatabaseUpdate = when (this) {
    is FullUpdate.Notification -> DatabaseUpdate(
        id = id,
        type = DatabaseUpdate.Type.Notification,
        underlyingId = notificationId.long
    )
}
