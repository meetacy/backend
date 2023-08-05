package app.meetacy.backend.infrastructure.database.notifications.view

import app.meetacy.backend.database.integration.notifications.DatabaseViewNotificationsUsecaseStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.viewNotificationStorage: ViewNotificationsUsecase.Storage by Dependency

fun DIBuilder.viewNotification() {
    val viewNotificationStorage by singleton<ViewNotificationsUsecase.Storage> {
        DatabaseViewNotificationsUsecaseStorage(database)
    }
}
