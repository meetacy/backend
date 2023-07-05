package app.meetacy.backend.infrastructure.integrations.notifications.view

import app.meetacy.backend.database.integration.notifications.DatabaseViewNotificationsUsecaseStorage
import app.meetacy.backend.database.integration.types.UsecaseViewNotificationsRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.notifications.ViewNotificationsUsecase
import app.meetacy.backend.usecase.types.ViewNotificationsRepository

val DI.viewNotificationsRepository: ViewNotificationsRepository by Dependency

fun DIBuilder.viewNotificationsRepository() {
    val viewNotificationsRepository by singleton<ViewNotificationsRepository> {
        UsecaseViewNotificationsRepository(
            usecase = ViewNotificationsUsecase(
                storage = DatabaseViewNotificationsUsecaseStorage(database),
                meetingsRepository = getMeetingsViewsRepository(database),
                usersRepository = getUserViewsRepository
            )
        )
    }
}
