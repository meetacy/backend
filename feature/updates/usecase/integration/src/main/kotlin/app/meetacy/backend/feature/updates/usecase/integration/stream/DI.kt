package app.meetacy.backend.feature.updates.usecase.integration.stream

import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.notification.GetNotificationsViewsRepository
import app.meetacy.backend.types.update.FullUpdate
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow

fun DIBuilder.streamUpdatesUsecase() {
    val streamUpdatesUsecase by singleton<StreamUpdatesUsecase> {
        val authRepository: AuthRepository by getting
        val notificationRepository: GetNotificationsViewsRepository by getting
        val storage = object : StreamUpdatesUsecase.Storage {
            val updatesStorage: UpdatesMiddleware by getting
            override suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate> =
                updatesStorage.updatesFlow(userId, fromId)
        }

        StreamUpdatesUsecase(authRepository, storage, notificationRepository)
    }
}
