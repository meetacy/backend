package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware

fun updatesDependenciesFactory(
    authRepository: AuthRepository,
    getNotificationsViewsRepository: GetNotificationsViewsRepository,
    updatesMiddleware: UpdatesMiddleware
): UpdatesDependencies = UpdatesDependencies(
    streamUpdatesRepository = UsecaseStreamUpdatesRepository(
        usecase = StreamUpdatesUsecase(
            auth = authRepository,
            notificationsRepository = getNotificationsViewsRepository,
            updatesMiddleware = updatesMiddleware
        )
    )
)
