package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.notifications.getNotificationViewsRepository
import app.meetacy.backend.infrastructure.factories.notifications.updatesMiddlewareBuilder
import app.meetacy.backend.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import org.jetbrains.exposed.sql.Database

fun updatesDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getNotificationsViewsRepository: GetNotificationsViewsRepository = getNotificationViewsRepository(db),
    updatesMiddleware: UpdatesMiddleware = updatesMiddlewareBuilder(db)
): UpdatesDependencies = UpdatesDependencies(
    streamUpdatesRepository = UsecaseStreamUpdatesRepository(
        usecase = StreamUpdatesUsecase(
            auth = authRepository,
            notificationsRepository = getNotificationsViewsRepository,
            updatesMiddleware = updatesMiddleware
        )
    )
)
