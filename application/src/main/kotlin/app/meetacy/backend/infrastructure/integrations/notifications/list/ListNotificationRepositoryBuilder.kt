package app.meetacy.backend.infrastructure.integrations.notifications.list

import app.meetacy.backend.database.integration.notifications.GetNotificationsUsecase
import app.meetacy.backend.endpoint.notifications.get.ListNotificationsRepository
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseListNotificationsRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun listNotificationRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): ListNotificationsRepository = UsecaseListNotificationsRepository(
    usecase = GetNotificationsUsecase(
        db = db,
        authRepository = authRepository,
        meetingsRepository = getMeetingsViewsRepository,
        usersRepository = getUsersViewsRepository
    )
)
