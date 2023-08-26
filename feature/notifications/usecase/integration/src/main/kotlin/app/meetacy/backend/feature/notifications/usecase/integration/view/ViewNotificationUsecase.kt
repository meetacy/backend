package app.meetacy.backend.feature.notifications.usecase.integration.view

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.backend.feature.notifications.usecase.get.ViewNotificationsUsecase
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.viewNotification() {
    val viewNotificationUsecase by singleton<ViewNotificationsUsecase> {
        val meetingsRepository: GetMeetingsViewsRepository by getting
        val usersRepository: GetUsersViewsRepository by getting
        val storage = object : ViewNotificationsUsecase.Storage {
            private val lastReadNotificationsStorage: LastReadNotificationsStorage by getting

            override suspend fun getLastReadNotificationId(userId: UserId): NotificationId {
                return lastReadNotificationsStorage.getLastReadNotificationId(userId)
            }
        }

        ViewNotificationsUsecase(storage, meetingsRepository, usersRepository)
    }
}
