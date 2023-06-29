package app.meetacy.backend.usecase.updates.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class StreamUpdatesUsecase(
    private val auth: AuthRepository,
    private val storage: Storage,
    private val notificationsRepository: GetNotificationsViewsRepository
) {
    suspend fun flow(
        accessIdentity: AccessIdentity,
        fromId: UpdateId?
    ): Result {
        val userId = auth.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val flow = storage
            .updatesFlow(userId, fromId)
            .map { update -> update.mapToView(userId) }

        return Result.Ready(flow)
    }

    private suspend fun FullUpdate.mapToView(viewerId: UserId): UpdateView = when (this) {
        is FullUpdate.Notification -> {
            UpdateView.Notification(
                id = id,
                notification = notificationsRepository.getNotificationView(viewerId, notificationId)
            )
        }
    }

    sealed interface Result {
        object TokenInvalid : Result
        class Ready(val flow: Flow<UpdateView>) : Result
    }

    interface Storage {
        suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate>
    }
}
