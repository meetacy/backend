package app.meetacy.backend.infrastructure.usecase.updates.stream

import app.meetacy.backend.endpoint.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.notifications.get.getNotificationViewsRepository
import app.meetacy.backend.infrastructure.database.updates.streamUpdatesStorage
import app.meetacy.backend.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository
import app.meetacy.backend.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.streamUpdatesRepository: StreamUpdatesRepository by Dependency

fun DIBuilder.streamUpdatesRepository() {
    val streamUpdatesRepository by singleton<StreamUpdatesRepository> {
        UsecaseStreamUpdatesRepository(
            usecase = StreamUpdatesUsecase(
                auth = authRepository,
                notificationsRepository = getNotificationViewsRepository,
                storage = streamUpdatesStorage
            )
        )
    }
}
