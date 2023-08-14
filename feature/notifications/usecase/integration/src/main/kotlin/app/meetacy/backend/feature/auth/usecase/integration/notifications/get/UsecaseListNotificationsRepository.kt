package app.meetacy.backend.feature.auth.usecase.integration.notifications.get

import app.meetacy.backend.endpoint.notifications.get.ListNotificationsRepository
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type

class UsecaseListNotificationsRepository(
    private val usecase: GetNotificationsUsecase
) : ListNotificationsRepository {
    override suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        pagingId: PagingId?,
        amount: Amount
    ): ListNotificationsRepository.Result = when (
        val result = usecase.getNotifications(
            accessIdentity = accessIdentity.type(),
            pagingId = pagingId,
            amount = amount.type()
        )
    ) {
        is GetNotificationsUsecase.Result.Success ->
            ListNotificationsRepository.Result.Success(
                notifications = result.notifications.mapItems { notification ->
                    notification.mapToEndpoint()
                }
            )
        is GetNotificationsUsecase.Result.TokenInvalid -> ListNotificationsRepository.Result.InvalidIdentity
    }
}
