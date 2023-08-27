package app.meetacy.backend.types.serializable.update

import app.meetacy.backend.types.serializable.notification.mapToEndpoint
import app.meetacy.backend.types.update.UpdateView

fun UpdateView.mapToEndpoint(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        id = id.serializable(),
        notification = notification.mapToEndpoint()
    )
}
