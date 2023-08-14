package app.meetacy.backend.feature.usecase.integration.types

import app.meetacy.backend.endpoint.types.updates.Update
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.types.UpdateView
import app.meetacy.backend.types.serializable.update.serializable

fun UpdateView.mapToEndpoint(): Update = when (this) {
    is UpdateView.Notification -> Update.Notification(
        id = id.serializable(),
        notification = notification.mapToEndpoint()
    )
}
