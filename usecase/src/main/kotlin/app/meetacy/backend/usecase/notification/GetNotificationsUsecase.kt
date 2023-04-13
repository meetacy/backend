package app.meetacy.backend.usecase.notification

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

class GetNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val usersRepository: GetUsersViewsRepository,
    private val meetingsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        offset: Long,
        count: Int
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val lastReadNotificationId = storage.getLastReadNotification(userId)

        val notifications = storage
            .getNotifications(userId, offset, count)

        val usersIterator = notifications
            .map { notification ->
                when (notification) {
                    is NotificationFromStorage.Invitation ->
                        notification.inviterId
                    is NotificationFromStorage.Subscription ->
                        notification.subscriberId
                }
            }
            .let { usersRepository.getUsersViews(userId, it) }
            .iterator()

        val meetingsIterator = notifications
            .filterIsInstance<NotificationFromStorage.Invitation>()
            .map { it.idMeeting }
            .let { meetingsRepository.getMeetingsViews(userId, it) }
            .iterator()

        val result = notifications
            .map { notification ->
                val isNew = notification.id.long <= lastReadNotificationId.long

                when (notification) {
                    is NotificationFromStorage.Invitation ->
                        Notification.Invitation(
                            notification.id, isNew,
                            meetingsIterator.next(),
                            usersIterator.next(),
                            notification.date
                        )
                    is NotificationFromStorage.Subscription ->
                        Notification.Subscription(
                            notification.id, isNew,
                            usersIterator.next(), notification.date
                        )
                }
            }

        return Result.Success(result)
    }

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val notifications: List<Notification>) : Result
    }

    sealed interface NotificationFromStorage {
        val id: NotificationId

        class Subscription(
            override val id: NotificationId,
            val subscriberId: UserId,
            val date: Date
        ) : NotificationFromStorage

        class Invitation(
            override val id: NotificationId,
            val idMeeting: IdMeeting,
            val inviterId: UserId,
            val date: Date
        ) : NotificationFromStorage
    }

    interface Storage {
        suspend fun getLastReadNotification(userId: UserId): NotificationId
        suspend fun getNotifications(userId: UserId, offset: Long, count: Int): List<NotificationFromStorage>
    }
}
