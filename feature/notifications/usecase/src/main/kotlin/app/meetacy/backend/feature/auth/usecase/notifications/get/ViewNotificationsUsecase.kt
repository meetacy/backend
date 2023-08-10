package app.meetacy.backend.feature.auth.usecase.notifications.get

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

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
