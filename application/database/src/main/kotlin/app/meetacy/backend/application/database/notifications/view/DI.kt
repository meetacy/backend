package app.meetacy.backend.application.database.notifications.view

import app.meetacy.backend.feature.notifications.database.integration.notifications.DatabaseViewNotificationsUsecaseStorage
import app.meetacy.backend.feature.notifications.database.integration.types.UsecaseViewNotificationsRepository
import app.meetacy.backend.application.database.database
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.notifications.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.feature.notifications.usecase.types.ViewNotificationsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.viewNotificationStorage: ViewNotificationsUsecase.Storage by Dependency
val DI.viewNotificationsRepository: ViewNotificationsRepository by Dependency

fun DIBuilder.viewNotification() {
    val viewNotificationStorage by singleton<ViewNotificationsUsecase.Storage> {
        DatabaseViewNotificationsUsecaseStorage(database)
    }
    val viewNotificationsRepository by singleton<ViewNotificationsRepository> {
        UsecaseViewNotificationsRepository(
            usecase = ViewNotificationsUsecase(
                storage = this.viewNotificationStorage,
                meetingsRepository = getMeetingViewRepository,
                usersRepository = getUserViewsRepository
            )
        )
    }
}
