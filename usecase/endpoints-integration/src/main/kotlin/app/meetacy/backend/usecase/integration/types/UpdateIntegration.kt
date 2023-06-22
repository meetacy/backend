package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.updates.Update
import app.meetacy.backend.usecase.types.UpdateView

fun UpdateView.mapToEndpoint(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        notification = notification.mapToEndpoint()
    )
}
