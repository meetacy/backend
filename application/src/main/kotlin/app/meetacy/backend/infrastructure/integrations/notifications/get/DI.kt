@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications.get

import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationsViewsUsecaseStorage
import app.meetacy.backend.database.integration.types.UsecaseGetNotificationsViewsRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.notifications.view.viewNotificationsRepository
import app.meetacy.backend.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository

val DI.getNotificationViewsRepository: GetNotificationsViewsRepository by Dependency

fun DIBuilder.getNotificationViewsRepository() {
    viewNotificationsRepository()
    val getNotificationViewsRepository by singleton<GetNotificationsViewsRepository> {
        UsecaseGetNotificationsViewsRepository(
            usecase = GetNotificationsViewsUsecase(
                storage = DatabaseGetNotificationsViewsUsecaseStorage(database),
                viewRepository = viewNotificationsRepository
            )
        )
    }
}
