package app.meetacy.backend.feature.updates.usecase.integration.types

import app.meetacy.backend.feature.updates.endpoints.types.updates.Update
import app.meetacy.backend.feature.notifications.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.types.serializable.update.serializable
import app.meetacy.backend.feature.updates.usecase.types.UpdateView

fun UpdateView.mapToEndpoint(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        id = id.serializable(),
        notification = notification.mapToEndpoint()
    )
}
