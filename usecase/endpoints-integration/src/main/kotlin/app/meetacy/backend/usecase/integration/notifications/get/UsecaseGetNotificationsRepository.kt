package app.meetacy.backend.usecase.integration.notifications.get

import app.meetacy.backend.endpoint.notifications.get.GetNotificationsRepository
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.notifications.GetNotificationsUsecase

class UsecaseGetNotificationsRepository(
    private val usecase: GetNotificationsUsecase
) : GetNotificationsRepository {
    override suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        pagingId: PagingId?,
        amount: Amount
    ): GetNotificationsRepository.Result = when (
        val result = usecase.getNotifications(
            accessIdentity = accessIdentity,
            pagingId = pagingId,
            amount = amount
        )
    ) {
        is GetNotificationsUsecase.Result.Success ->
            GetNotificationsRepository.Result.Success(
                notifications = result.notifications.mapItems { notification ->
                    notification.mapToEndpoint()
                }
            )
        is GetNotificationsUsecase.Result.TokenInvalid -> GetNotificationsRepository.Result.InvalidIdentity
    }
}
