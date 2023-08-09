package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.database.integration.notifications.ViewNotificationsUsecase
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.usecase.types.*
import org.jetbrains.exposed.sql.Database

fun ViewNotificationsRepository(
    db: Database,
    meetingsRepository: GetMeetingsViewsRepository,
    usersRepository: GetUsersViewsRepository
): ViewNotificationsRepository = UsecaseViewNotificationsRepository(
    usecase = ViewNotificationsUsecase(db, meetingsRepository, usersRepository)
)

class UsecaseViewNotificationsRepository(
    val usecase: ViewNotificationsUsecase
) : ViewNotificationsRepository {
    override suspend fun viewNotifications(
        viewerId: UserId,
        notifications: List<FullNotification>
    ): List<NotificationView> = usecase.viewNotifications(viewerId, notifications)
}
