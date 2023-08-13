package app.meetacy.backend.infrastructure.usecase.notifications.read

import app.meetacy.backend.feature.notifications.endpoints.read.ReadNotificationsRepository
import app.meetacy.backend.infrastructure.database.notifications.read.readNotificationStorage
import app.meetacy.backend.feature.notifications.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.feature.notifications.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.readNotificationsRepository: ReadNotificationsRepository by Dependency

fun DIBuilder.readNotificationsRepository() {
    val readNotificationsRepository by singleton<ReadNotificationsRepository> {
        UsecaseReadNotificationsRepository(
            usecase = ReadNotificationsUsecase(
                authRepository = get(),
                storage = readNotificationStorage
            )
        )
    }
}
