package app.meetacy.backend.application.database.notifications.get

import app.meetacy.backend.feature.notifications.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.feature.notifications.database.integration.notifications.DatabaseGetNotificationsViewsUsecaseStorage
import app.meetacy.backend.feature.notifications.database.integration.types.UsecaseGetNotificationsViewsRepository
import app.meetacy.backend.application.database.database
import app.meetacy.backend.infrastructure.database.notifications.view.viewNotificationsRepository
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.feature.notifications.usecase.types.GetNotificationsViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getNotificationViewStorage: GetNotificationsViewsUsecase.Storage by Dependency
val DI.getNotificationStorage: GetNotificationsUsecase.Storage by Dependency
val DI.getNotificationViewsRepository: GetNotificationsViewsRepository by Dependency

fun DIBuilder.getNotification() {
    val getNotificationStorage by singleton<GetNotificationsViewsUsecase.Storage> {
        DatabaseGetNotificationsViewsUsecaseStorage(database)
    }
    val getNotificationViewStorage by singleton<GetNotificationsUsecase.Storage> {
        DatabaseGetNotificationStorage(database)
    }
    val getNotificationViewsRepository by singleton<GetNotificationsViewsRepository> {
        UsecaseGetNotificationsViewsRepository(
            usecase = GetNotificationsViewsUsecase(
                storage = this.getNotificationViewStorage,
                viewRepository = viewNotificationsRepository
            )
        )
    }
}
