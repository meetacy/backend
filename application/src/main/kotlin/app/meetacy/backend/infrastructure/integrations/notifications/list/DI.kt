@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications.list

import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.notifications.get.ListNotificationsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.notifications.view.viewNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.usecase.notifications.GetNotificationsUsecase

val DI.listNotificationsRepository: ListNotificationsRepository by Dependency

fun DIBuilder.listNotificationsRepository() {
    val listNotificationRepository by singleton<ListNotificationsRepository> {
        UsecaseListNotificationsRepository(
            usecase = GetNotificationsUsecase(
                authRepository,
                storage = DatabaseGetNotificationStorage(database),
                viewNotificationsRepository = viewNotificationsRepository
            )
        )
    }
}
