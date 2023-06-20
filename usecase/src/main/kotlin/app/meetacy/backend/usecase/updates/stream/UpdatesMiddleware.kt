package app.meetacy.backend.usecase.updates.stream

import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUpdate
import kotlinx.coroutines.flow.*

class UpdatesMiddleware(private val storage: Storage) {
    private val allUpdatesFlow = MutableSharedFlow<UserUpdate>()

    suspend fun addUpdate(userId: UserId, update: FullUpdate) {
        storage.addUpdate(userId, update)
        allUpdatesFlow.emit(
            value = UserUpdate(userId, update)
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
        suspend fun addUpdate(userId: UserId, update: FullUpdate)
        fun pastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate>
    }

    private data class UserUpdate(
        val userId: UserId,
        val update: FullUpdate
    )
}
