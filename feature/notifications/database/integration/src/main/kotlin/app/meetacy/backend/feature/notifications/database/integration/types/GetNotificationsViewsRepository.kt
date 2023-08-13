package app.meetacy.backend.feature.notifications.database.integration.types

import app.meetacy.backend.feature.notifications.database.integration.notifications.GetNotificationsViewsUsecase
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.notifications.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.feature.notifications.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.feature.notifications.usecase.types.NotificationView
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
