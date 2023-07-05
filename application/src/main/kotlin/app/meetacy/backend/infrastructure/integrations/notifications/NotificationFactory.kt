package app.meetacy.backend.infrastructure.integrations.notifications

import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.integrations.notifications.list.listNotificationRepository
import app.meetacy.backend.infrastructure.integrations.notifications.read.readNotificationRepository
import org.jetbrains.exposed.sql.Database

fun notificationDependenciesFactory(
    db: Database
): NotificationsDependencies = NotificationsDependencies(
    listNotificationsRepository = listNotificationRepository(db),
    readNotificationsRepository = readNotificationRepository(db)
)
