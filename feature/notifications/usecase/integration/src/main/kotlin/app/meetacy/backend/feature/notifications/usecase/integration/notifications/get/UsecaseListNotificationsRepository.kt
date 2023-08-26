package app.meetacy.backend.feature.notifications.usecase.integration.notifications.get

import app.meetacy.backend.feature.notifications.endpoints.get.ListNotificationsRepository
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.feature.notifications.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsUsecase
import app.meetacy.backend.types.paging.serializable.PagingId as PagingIdSerializable

class UsecaseListNotificationsRepository(
    private val usecase: GetNotificationsUsecase
) : ListNotificationsRepository {
    override suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        pagingId: PagingIdSerializable?,
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
