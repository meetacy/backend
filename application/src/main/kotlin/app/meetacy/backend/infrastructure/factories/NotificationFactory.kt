package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun notificationDependenciesFactory(
    db: Database,
    authRepository: AuthRepository,
    getMeetingsViewsRepository: GetMeetingsViewsRepository,
    getUsersViewsRepository: GetUsersViewsRepository
): NotificationsDependencies = NotificationsDependencies(
    getNotificationsRepository = UsecaseGetNotificationsRepository(
        usecase = GetNotificationsUsecase(
            authRepository = authRepository,
            usersRepository = getUsersViewsRepository,
            meetingsRepository = getMeetingsViewsRepository,
            storage = DatabaseGetNotificationStorage(db)
        )
    ),
    readNotificationsRepository = UsecaseReadNotificationsRepository(
        usecase = ReadNotificationsUsecase(
            authRepository = authRepository,
            storage = DatabaseReadNotificationsStorage(db)
        )
    )
)
