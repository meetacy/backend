@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications.get

import app.meetacy.backend.database.integration.types.UsecaseGetNotificationsViewsRepository
import app.meetacy.backend.infrastructure.database.notifications.get.getNotificationViewStorage
import app.meetacy.backend.infrastructure.integrations.notifications.view.viewNotificationsRepository
import app.meetacy.backend.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getNotificationViewsRepository: GetNotificationsViewsRepository by Dependency

fun DIBuilder.getNotificationViewsRepository() {
    viewNotificationsRepository()
    val getNotificationViewsRepository by singleton<GetNotificationsViewsRepository> {
        UsecaseGetNotificationsViewsRepository(
            usecase = GetNotificationsViewsUsecase(
                storage = getNotificationViewStorage,
                viewRepository = viewNotificationsRepository
            )
        )
    }
}
