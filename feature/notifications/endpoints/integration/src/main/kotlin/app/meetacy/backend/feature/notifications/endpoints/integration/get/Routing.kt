package app.meetacy.backend.feature.notifications.endpoints.integration.get

import app.meetacy.backend.feature.notifications.endpoints.get.ListNotificationsRepository
import app.meetacy.backend.feature.notifications.endpoints.get.list
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsUsecase
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.notification.mapToEndpoint
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.list(di: DI) {
    val usecase: GetNotificationsUsecase by di.getting
    val repository = object : ListNotificationsRepository {
        override suspend fun getNotifications(
            accessIdentity: AccessIdentity,
            pagingId: PagingId?,
            amount: Amount
        ): ListNotificationsRepository.Result = when (
            val result = usecase.getNotifications(
                accessIdentity = accessIdentity.type(),
                pagingId = pagingId?.type(),
                amount = amount.type()
            )
        ) {
            is GetNotificationsUsecase.Result.Success ->
                ListNotificationsRepository.Result.Success(
                    notifications = result.notifications.mapItems { notification ->
                        notification.mapToEndpoint()
                    }.serializable()
                )
            is GetNotificationsUsecase.Result.TokenInvalid -> ListNotificationsRepository.Result.InvalidIdentity
        }
    }
    list(repository)
}
