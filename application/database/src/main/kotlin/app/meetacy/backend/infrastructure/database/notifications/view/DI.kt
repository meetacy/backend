package app.meetacy.backend.infrastructure.database.notifications.view

import app.meetacy.backend.database.integration.notifications.DatabaseViewNotificationsUsecaseStorage
import app.meetacy.backend.feature.auth.database.integration.types.UsecaseViewNotificationsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.usecase.types.ViewNotificationsRepository
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
