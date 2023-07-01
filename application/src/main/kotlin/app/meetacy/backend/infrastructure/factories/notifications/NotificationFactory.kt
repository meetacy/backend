package app.meetacy.backend.infrastructure.factories.notifications

import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.database.integration.notifications.GetNotificationsUsecase
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun notificationDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): NotificationsDependencies = NotificationsDependencies(
    listNotificationsRepository = UsecaseListNotificationsRepository(
        usecase = GetNotificationsUsecase(
            db = db,
            authRepository = authRepository,
            meetingsRepository = getMeetingsViewsRepository,
            usersRepository = getUsersViewsRepository
        )
    ),
    readNotificationsRepository = UsecaseReadNotificationsRepository(
        usecase = ReadNotificationsUsecase(
            authRepository = authRepository,
            storage = DatabaseReadNotificationsStorage(db)
        )
    )
)
