package app.meetacy.backend.feature.notifications.database.integration

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database


internal fun DIBuilder.lastReadNotificationsStorage() {
    val lastReadNotificationsStorage by singleton {
        val database: Database by getting
        LastReadNotificationsStorage(database)
    }
}
