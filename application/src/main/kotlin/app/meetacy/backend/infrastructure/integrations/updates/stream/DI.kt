package app.meetacy.backend.infrastructure.integrations.updates.stream

import app.meetacy.backend.database.integration.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.notifications.add.updatesMiddleware
import app.meetacy.backend.infrastructure.integrations.notifications.list.getNotificationViewsRepository
import app.meetacy.backend.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository

val DI.streamUpdatesRepository: StreamUpdatesRepository by Dependency

fun DIBuilder.streamUpdatesRepository() {
    val streamUpdatesRepository by singleton<StreamUpdatesRepository> {
        UsecaseStreamUpdatesRepository(
            usecase = StreamUpdatesUsecase(
                auth = authRepository,
                notificationsRepository = getNotificationViewsRepository(database),
                updatesMiddleware = updatesMiddleware
            )
        )
    }
}
