package app.meetacy.backend.feature.friends.endpoints.integration.relationship.subscriptions

import app.meetacy.backend.feature.friends.endpoints.relationship.subscriptions.GetSubscriptionsRepository
import app.meetacy.backend.feature.friends.endpoints.relationship.subscriptions.GetSubscriptionsResult
import app.meetacy.backend.feature.friends.endpoints.relationship.subscriptions.getSubscriptions
import app.meetacy.backend.feature.friends.usecase.relationship.subscriptions.GetSubscriptionsUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.subscriptions(di: DI) {
    val usecase: GetSubscriptionsUsecase by di.getting
    val repository = object : GetSubscriptionsRepository {
        override suspend fun getSubscriptions(identifier: GetSubscriptionsRepository.Identifier): GetSubscriptionsResult {
            return when(
                val result = usecase.getSubscriptions(
                    when (identifier) {
                        is GetSubscriptionsRepository.Identifier.ByUserId -> GetSubscriptionsUsecase.Identifier.ByUserId(
                            identity = identifier.userId.type(),
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                        is GetSubscriptionsRepository.Identifier.Self -> GetSubscriptionsUsecase.Identifier.Self(
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                    }
                )
            ) {
                GetSubscriptionsUsecase.Result.InvalidToken -> GetSubscriptionsResult.InvalidIdentity
                GetSubscriptionsUsecase.Result.UserNotFound -> GetSubscriptionsResult.UserNotFound
                is GetSubscriptionsUsecase.Result.Success -> GetSubscriptionsResult.Success(
                    result.paging.map { userDetailsList -> userDetailsList.map { it.serializable() } }.serializable()
                )
            }
        }
    }
    getSubscriptions(repository)
}
