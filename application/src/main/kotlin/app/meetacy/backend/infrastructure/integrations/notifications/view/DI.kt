package app.meetacy.backend.infrastructure.integrations.notifications.view

import app.meetacy.backend.database.integration.notifications.DatabaseViewNotificationsUsecaseStorage
import app.meetacy.backend.database.integration.types.UsecaseViewNotificationsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
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
                storage = DatabaseViewNotificationsUsecaseStorage(database),
                meetingsRepository = getMeetingsViewsRepository(database),
                usersRepository = getUserViewsRepository
            )
        )
    }
}
