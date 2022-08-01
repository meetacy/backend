package app.meetacy.backend.usecase.integration.notifications.get

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.endpoint.notifications.get.GetNotificationsRepository
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase

class UsecaseGetNotificationsRepository(
    private val usecase: GetNotificationsUsecase
) : GetNotificationsRepository {
    override suspend fun getNotifications(
        accessToken: AccessToken,
        offset: Long,
        amount: Int
    ): GetNotificationsRepository.Result = when (
        val result = usecase.getNotifications(
            accessToken = accessToken,
            offset = offset,
            count = amount
        )
    ) {
        is GetNotificationsUsecase.Result.Success ->
            GetNotificationsRepository.Result.Success(
                notifications = result.notifications.map { notification ->
                    notification.mapToEndpoint()
                }
            )
        is GetNotificationsUsecase.Result.TokenInvalid -> GetNotificationsRepository.Result.TokenInvalid
    }
}
