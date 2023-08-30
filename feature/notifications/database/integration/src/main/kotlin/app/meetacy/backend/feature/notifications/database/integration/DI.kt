package app.meetacy.backend.feature.notifications.database.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.notifications() {
    lastReadNotificationsStorage()
    notificationsStorage()
}