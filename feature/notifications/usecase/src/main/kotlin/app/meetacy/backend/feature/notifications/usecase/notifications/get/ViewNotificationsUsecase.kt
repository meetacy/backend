package app.meetacy.backend.feature.notifications.usecase.notifications.get

import app.meetacy.backend.feature.notifications.usecase.types.FullNotification
import app.meetacy.backend.feature.notifications.usecase.types.NotificationView
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.getMeetingsViews
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.getUsersViews

class ViewNotificationsUsecase(
    private val storage: Storage,
    private val meetingsRepository: GetMeetingsViewsRepository,
    private val usersRepository: GetUsersViewsRepository
) {

    suspend fun viewNotifications(
        viewerId: UserId,
        notifications: List<FullNotification>
    ): List<NotificationView> {
        val lastReadNotificationId = storage.getLastReadNotificationId(viewerId)

        val usersIterator = notifications
            .map { notification ->
                when (notification) {
                    is FullNotification.Invitation ->
                        notification.inviterId
                    is FullNotification.Subscription ->
                        notification.subscriberId
                }
            }
            .let { usersRepository.getUsersViews(viewerId, it) }
            .iterator()

        val meetingsIterator = notifications
            .filterIsInstance<FullNotification.Invitation>()
            .map { it.meetingId }
            .let { meetingsRepository.getMeetingsViews(viewerId, it) }
            .iterator()

        return notifications
            .map { notification ->
                val isNew = notification.id.long <= lastReadNotificationId.long

                when (notification) {
                    is FullNotification.Invitation ->
                        NotificationView.Invitation(
                            notification.id, isNew,
                            notification.date,
                            meetingsIterator.next(),
                            usersIterator.next()
                        )

                    is FullNotification.Subscription ->
                        NotificationView.Subscription(
                            notification.id, isNew,
                            notification.date, usersIterator.next()
                        )
                }
            }
    }

    interface Storage {
        suspend fun getLastReadNotificationId(userId: UserId): NotificationId
    }
}
