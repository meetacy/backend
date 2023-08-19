package app.meetacy.backend.application.database.notifications

import app.meetacy.backend.feature.notifications.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.infrastructure.database.notifications.add.addNotification
import app.meetacy.backend.infrastructure.database.notifications.get.getNotification
import app.meetacy.backend.infrastructure.database.notifications.read.readNotification
import app.meetacy.backend.infrastructure.database.notifications.view.viewNotification
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.notificationsStorage: NotificationsStorage by Dependency
val DI.lastReadNotificationsStorage: LastReadNotificationsStorage by Dependency

fun DIBuilder.notifications() {
    addNotification()
    getNotification()
    readNotification()
    viewNotification()
    val notificationsStorage by singleton { NotificationsStorage(database) }
    val lastReadNotificationsStorage by singleton { LastReadNotificationsStorage(database) }
}
