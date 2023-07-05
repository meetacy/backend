@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.notifications.add.addNotificationUsecase
import app.meetacy.backend.infrastructure.integrations.notifications.list.listNotificationRepository
import app.meetacy.backend.infrastructure.integrations.notifications.read.readNotificationRepository

val DI.notificationsDependencies: NotificationsDependencies by Dependency

fun DIBuilder.notifications() {
    addNotificationUsecase()
    val notificationsDependencies by singleton<NotificationsDependencies> {
        NotificationsDependencies(
            listNotificationsRepository = listNotificationRepository(database),
            readNotificationsRepository = readNotificationRepository(database)
        )
    }
    // TODO: migrate to the new DI
}
