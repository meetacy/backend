package app.meetacy.backend.types.integration.notification

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.notification() {
    viewNotifications()
    getNotificationViews()
}
