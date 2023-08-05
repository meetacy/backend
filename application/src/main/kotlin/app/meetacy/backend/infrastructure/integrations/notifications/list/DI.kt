@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications.list

import app.meetacy.backend.endpoint.notifications.get.ListNotificationsRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.notifications.get.getNotificationStorage
import app.meetacy.backend.infrastructure.integrations.notifications.view.viewNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listNotificationsRepository: ListNotificationsRepository by Dependency

fun DIBuilder.listNotificationsRepository() {
    val listNotificationRepository by singleton<ListNotificationsRepository> {
        UsecaseListNotificationsRepository(
            usecase = GetNotificationsUsecase(
                authRepository,
                storage = getNotificationStorage,
                viewNotificationsRepository = viewNotificationsRepository
            )
        )
    }
}
