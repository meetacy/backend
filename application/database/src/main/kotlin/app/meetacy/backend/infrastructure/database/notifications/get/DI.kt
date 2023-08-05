package app.meetacy.backend.infrastructure.database.notifications.get

import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationsViewsUsecaseStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getNotificationViewStorage: GetNotificationsViewsUsecase.Storage by Dependency
val DI.getNotificationStorage: GetNotificationsUsecase.Storage by Dependency

fun DIBuilder.getNotification() {
    val getNotificationStorage by singleton<GetNotificationsViewsUsecase.Storage> {
        DatabaseGetNotificationsViewsUsecaseStorage(database)
    }
    val getNotificationViewStorage by singleton<GetNotificationsUsecase.Storage> {
        DatabaseGetNotificationStorage(database)
    }
}
