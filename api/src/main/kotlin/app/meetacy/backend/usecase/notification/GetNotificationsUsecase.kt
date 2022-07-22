package app.meetacy.backend.usecase.notification

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.Notification
import app.meetacy.backend.usecase.types.authorize
import app.meetacy.backend.usecase.types.getMeetingsViews
import app.meetacy.backend.usecase.types.getUsersViews

class GetNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val usersRepository: GetUsersViewsRepository,
    private val meetingsRepository: GetMeetingsViewsRepository,
    private val storage: Storage
) {
    suspend fun getNotifications(
        accessToken: AccessToken,
        offset: Long,
        count: Int
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }

        val lastReadNotificationId = storage.getLastReadNotification(userId)

        val notifications = storage
            .getNotifications(userId, offset, count)

        val usersIterator = notifications
            .filterIsInstance<NotificationFromStorage.Subscription>()
            .map { it.subscriberId }
            .let { usersRepository.getUsersViews(userId, it) }
            .iterator()

        val meetingsIterator = notifications
            .filterIsInstance<NotificationFromStorage.Invitation>()
            .map { it.meetingId }
            .let { meetingsRepository.getMeetingsViews(userId, it) }
            .iterator()

        val result = notifications
            .map { notification ->
                val isNew = notification.id.long <= lastReadNotificationId.long

                when (notification) {
                    is NotificationFromStorage.Invitation ->
                        Notification.Invitation(
                            notification.id, isNew,
                            meetingsIterator.next(), notification.date
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
        class Success(val result: List<Notification>) : Result
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
            val meetingId: MeetingId,
            val date: Date
        ) : NotificationFromStorage
    }

    interface Storage {
        suspend fun getLastReadNotification(userId: UserId): NotificationId
        suspend fun getNotifications(userId: UserId, offset: Long, count: Int): List<NotificationFromStorage>
    }
}
