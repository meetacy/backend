@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.integrations.notifications.add.addNotificationUsecase
import app.meetacy.backend.infrastructure.integrations.notifications.list.listNotificationsRepository
import app.meetacy.backend.infrastructure.integrations.notifications.read.readNotificationsRepository

val DI.notificationsDependencies: NotificationsDependencies by Dependency

fun DIBuilder.notifications() {
    addNotificationUsecase()
    listNotificationsRepository()
    readNotificationsRepository()
    val notificationsDependencies by singleton<NotificationsDependencies> {
        NotificationsDependencies(
            listNotificationsRepository = listNotificationsRepository,
            readNotificationsRepository = readNotificationsRepository
        )
    }
}
