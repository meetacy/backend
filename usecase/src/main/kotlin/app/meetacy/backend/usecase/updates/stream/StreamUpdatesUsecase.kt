package app.meetacy.backend.usecase.updates.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StreamUpdatesUsecase(
    private val auth: AuthRepository,
    private val storage: Storage
) {
    suspend fun stream(
        accessIdentity: AccessIdentity,
        fromId: UpdateId?,
        channel: SendChannel<UpdateView>
    ): Result {
        val userId = auth.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        storage
            .updatesFlow(userId, fromId)
            .map { update -> update.mapToView() }
            .collect(channel::send)

        return Result.LocationStreamed
    }

    private suspend fun FullUpdate.mapToView(): UpdateView = when (this) {
        is FullUpdate.Notification -> UpdateView.Notification(
            id = id,
            notification = storage.getNotification(notificationId)
        )
    }

    sealed interface Result {
        object TokenInvalid : Result
        object LocationStreamed : Result
    }

    interface Storage {
        suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate>
        suspend fun getNotification(notificationId: NotificationId): NotificationView
    }
}
