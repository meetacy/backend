package app.meetacy.backend.infrastructure.usecase.updates.stream

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.infrastructure.database.notifications.get.getNotificationViewsRepository
import app.meetacy.backend.infrastructure.database.updates.streamUpdatesStorage
import app.meetacy.backend.feature.updates.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.streamUpdatesRepository: StreamUpdatesRepository by Dependency

fun DIBuilder.streamUpdatesRepository() {
    val streamUpdatesRepository by singleton<StreamUpdatesRepository> {
        UsecaseStreamUpdatesRepository(
            usecase = StreamUpdatesUsecase(
                auth = get(),
                notificationsRepository = getNotificationViewsRepository,
                storage = streamUpdatesStorage
            )
        )
    }
}
