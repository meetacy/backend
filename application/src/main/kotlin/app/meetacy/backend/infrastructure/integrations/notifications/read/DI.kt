package app.meetacy.backend.infrastructure.integrations.notifications.read

import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase

val DI.readNotificationsRepository: ReadNotificationsRepository by Dependency

fun DIBuilder.readNotificationsRepository() {
    val readNotificationsRepository by singleton<ReadNotificationsRepository> {
        UsecaseReadNotificationsRepository(
            usecase = ReadNotificationsUsecase(
                authRepository = authRepository,
                storage = DatabaseReadNotificationsStorage(database)
            )
        )
    }
}
