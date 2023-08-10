package app.meetacy.backend.feature.auth.usecase.integration.types

import app.meetacy.backend.endpoint.types.updates.Update
import app.meetacy.backend.types.serializable.update.serializable
import app.meetacy.backend.usecase.types.UpdateView

fun UpdateView.mapToEndpoint(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        id = id.serializable(),
        notification = notification.mapToEndpoint()
    )
}
