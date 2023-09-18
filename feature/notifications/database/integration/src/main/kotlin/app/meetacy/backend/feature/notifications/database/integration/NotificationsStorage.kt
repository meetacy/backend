package app.meetacy.backend.feature.notifications.database.integration

import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.notificationsStorage() {
    val notificationsStorage by singleton {
        val database: Database by getting
        NotificationsStorage(database)
    }
}
