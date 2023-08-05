package app.meetacy.backend.infrastructure.integrations.notifications

import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.integrations.notifications.list.listNotificationsRepository
import app.meetacy.backend.infrastructure.integrations.notifications.read.readNotificationsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.notificationsDependencies: NotificationsDependencies by Dependency

fun DIBuilder.notifications() {
    listNotificationsRepository()
    readNotificationsRepository()
    val notificationsDependencies by singleton<NotificationsDependencies> {
        NotificationsDependencies(
            listNotificationsRepository = listNotificationsRepository,
            readNotificationsRepository = readNotificationsRepository
        )
    }
}
