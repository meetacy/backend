package app.meetacy.backend.infrastructure.factories.notifications.read

import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun readNotificationRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): ReadNotificationsRepository = UsecaseReadNotificationsRepository(
    usecase = ReadNotificationsUsecase(
        authRepository = authRepository,
        storage = DatabaseReadNotificationsStorage(db)
    )
)
