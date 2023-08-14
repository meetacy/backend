package app.meetacy.backend.feature.updates.usecase.updates.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.updates.usecase.types.FullUpdate
import app.meetacy.backend.feature.notifications.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.feature.updates.usecase.types.UpdateView
import app.meetacy.backend.feature.notifications.usecase.types.getNotificationView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
