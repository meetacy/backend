package app.meetacy.backend.infrastructure.usecase.notifications.list

import app.meetacy.backend.feature.notifications.endpoints.get.ListNotificationsRepository
import app.meetacy.backend.infrastructure.database.notifications.get.getNotificationStorage
import app.meetacy.backend.infrastructure.database.notifications.view.viewNotificationsRepository
import app.meetacy.backend.feature.notifications.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listNotificationsRepository: ListNotificationsRepository by Dependency

fun DIBuilder.listNotificationsRepository() {
    val listNotificationRepository by singleton<ListNotificationsRepository> {
        UsecaseListNotificationsRepository(
            usecase = GetNotificationsUsecase(
                authRepository = get(),
                storage = getNotificationStorage,
                viewNotificationsRepository = viewNotificationsRepository
            )
        )
    }
}
