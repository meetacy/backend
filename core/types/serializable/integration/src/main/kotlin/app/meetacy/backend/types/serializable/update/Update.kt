package app.meetacy.backend.types.serializable.update

import app.meetacy.backend.types.serializable.notification.serializable
import app.meetacy.backend.types.update.UpdateView

fun UpdateView.serializable(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        id = id.serializable(),
        notification = notification.serializable()
    )
}
