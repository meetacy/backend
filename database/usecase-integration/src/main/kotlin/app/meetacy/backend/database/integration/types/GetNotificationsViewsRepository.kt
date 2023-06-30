package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.integration.notifications.GetNotificationsViewsUsecase
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notifications.GetNotificationsViewsUsecase
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.NotificationView
import org.jetbrains.exposed.sql.Database

fun GetNotificationsViewsRepository(
    db: Database,
    getMeetingsViewsRepository: GetMeetingsViewsRepository,
    getUsersViewsRepository: GetUsersViewsRepository
): GetNotificationsViewsRepository {
    return UsecaseGetNotificationsViewsRepository(
        usecase = GetNotificationsViewsUsecase(
            db, getMeetingsViewsRepository, getUsersViewsRepository
        )
    )
}

class UsecaseGetNotificationsViewsRepository(
    private val usecase: GetNotificationsViewsUsecase
) : GetNotificationsViewsRepository {
    override suspend fun getNotificationsViewsOrNull(
        viewerId: UserId,
        notificationIds: List<NotificationId>
    ): List<NotificationView?> {
        return usecase.getNotificationsViewsOrNull(viewerId, notificationIds)
    }
}
