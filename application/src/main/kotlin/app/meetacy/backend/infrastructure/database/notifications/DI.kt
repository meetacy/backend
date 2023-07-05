@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.notifications

import app.meetacy.backend.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.notificationsStorage: NotificationsStorage by Dependency
val DI.lastReadNotificationsStorage: LastReadNotificationsStorage by Dependency

fun DIBuilder.notifications() {
    val notificationsStorage by singleton { NotificationsStorage(database) }
    val lastReadNotificationsStorage by singleton { LastReadNotificationsStorage(database) }
}
