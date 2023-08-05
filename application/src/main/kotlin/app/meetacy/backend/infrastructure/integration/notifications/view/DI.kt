package app.meetacy.backend.infrastructure.integration.notifications.view

import app.meetacy.backend.database.integration.types.UsecaseViewNotificationsRepository
import app.meetacy.backend.infrastructure.database.notifications.view.viewNotificationStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.usecase.types.ViewNotificationsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.viewNotificationsRepository: ViewNotificationsRepository by Dependency

fun DIBuilder.viewNotificationsRepository() {
    val viewNotificationsRepository by singleton<ViewNotificationsRepository> {
        UsecaseViewNotificationsRepository(
            usecase = ViewNotificationsUsecase(
                storage = viewNotificationStorage,
                meetingsRepository = getMeetingViewRepository,
                usersRepository = getUserViewsRepository
            )
        )
    }
}
