package app.meetacy.backend.infrastructure.factories.notifications

import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.notifications.list.listNotificationRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun notificationDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): NotificationsDependencies = NotificationsDependencies(
    listNotificationsRepository = listNotificationRepository(db),
    readNotificationsRepository = UsecaseReadNotificationsRepository(
        usecase = ReadNotificationsUsecase(
            authRepository = authRepository,
            storage = DatabaseReadNotificationsStorage(db)
        )
    )
)
