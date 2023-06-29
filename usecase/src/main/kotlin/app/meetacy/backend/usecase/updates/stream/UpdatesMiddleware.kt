package app.meetacy.backend.usecase.updates.stream

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUpdate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*

class UpdatesMiddleware(private val storage: Storage) {
    private val allUpdatesFlow = MutableSharedFlow<UserUpdate>()

    suspend fun addNotificationUpdate(userId: UserId, notificationId: NotificationId) {
        val updateId = storage.addNotificationUpdate(userId, notificationId)

        allUpdatesFlow.emit(
            value = UserUpdate(
                userId,
                update = FullUpdate.Notification(
                    id = updateId,
                    notificationId = notificationId
                )
            )
        )
    }

    fun updatesFlow(userId: UserId, fromId: UpdateId? = null): Flow<FullUpdate> {
        val pastUpdates = if (fromId == null) emptyFlow() else storage.pastUpdatesFlow(userId, fromId)
        return flow {
            emitAll(pastUpdates)
            val newUpdates = allUpdatesFlow
                .filter { (updateUserId) -> updateUserId == userId }
                .map { (_, update) -> update }
            emitAll(newUpdates)
        }
    }

    interface Storage {
        suspend fun addNotificationUpdate(userId: UserId, notificationId: NotificationId): UpdateId
        fun pastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate>
    }

    private data class UserUpdate(
        val userId: UserId,
        val update: FullUpdate
    )
}
