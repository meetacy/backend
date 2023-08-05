package app.meetacy.backend.infrastructure.integrations.notifications.read

import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.notifications.read.readNotificationStorage
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.readNotificationsRepository: ReadNotificationsRepository by Dependency

fun DIBuilder.readNotificationsRepository() {
    val readNotificationsRepository by singleton<ReadNotificationsRepository> {
        UsecaseReadNotificationsRepository(
            usecase = ReadNotificationsUsecase(
                authRepository = authRepository,
                storage = readNotificationStorage
            )
        )
    }
}
