package app.meetacy.backend.infrastructure.database.notifications.read

import app.meetacy.backend.feature.notifications.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.feature.notifications.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.readNotificationStorage: ReadNotificationsUsecase.Storage by Dependency

fun DIBuilder.readNotification() {
    val readNotificationStorage by singleton<ReadNotificationsUsecase.Storage> {
        DatabaseReadNotificationsStorage(database)
    }
}
