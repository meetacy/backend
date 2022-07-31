package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.LastReadNotificationStorage
import app.meetacy.backend.mock.storage.MockNotification
import app.meetacy.backend.mock.storage.NotificationsStorage
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase.NotificationFromStorage

private object GetNotificationStorage : GetNotificationsUsecase.Storage {
    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        LastReadNotificationStorage.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<NotificationFromStorage> = NotificationsStorage
        .getNotifications(userId, offset.toInt(), count)
        .map(MockNotification::mapToIntegration)
}

private fun MockNotification.mapToIntegration(): NotificationFromStorage =
    when (type) {
        MockNotification.Type.Subscription ->
            NotificationFromStorage.Subscription(
                id, subscriberId!!, date
            )
        MockNotification.Type.Invitation ->
            NotificationFromStorage.Invitation(
                id, invitedMeetingId!!, inviterId!!, date
            )
    }

fun mockGetNotificationsUsecase(): GetNotificationsUsecase =
    GetNotificationsUsecase(
        authRepository = MockAuthRepository,
        usersRepository = MockGetUsersViewsRepository,
        meetingsRepository = MockGetMeetingsViewsRepository,
        storage = GetNotificationStorage
    )
