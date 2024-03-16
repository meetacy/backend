package app.meetacy.backend.feature.friends.endpoints.integration.subscriptions.list

import app.meetacy.backend.feature.friends.endpoints.subscriptions.list.ListSubscriptionsRepository
import app.meetacy.backend.feature.friends.endpoints.subscriptions.list.ListSubscriptionsResult
import app.meetacy.backend.feature.friends.endpoints.subscriptions.list.listSubscriptions
import app.meetacy.backend.feature.friends.usecase.subscriptions.list.ListSubscriptionsUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.listSubscriptions(di: DI) {
    val usecase: ListSubscriptionsUsecase by di.getting
    val repository = object : ListSubscriptionsRepository {
        override suspend fun listSubscriptions(identifier: ListSubscriptionsRepository.Identifier): ListSubscriptionsResult {
            return when(
                val result = usecase.listSubscriptions(
                    when (identifier) {
                        is ListSubscriptionsRepository.Identifier.ByUserId -> ListSubscriptionsUsecase.Identifier.ByUserId(
                            identity = identifier.userId.type(),
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                        is ListSubscriptionsRepository.Identifier.Self -> ListSubscriptionsUsecase.Identifier.Self(
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                    }
                )
            ) {
                ListSubscriptionsUsecase.Result.InvalidToken -> ListSubscriptionsResult.InvalidIdentity
                ListSubscriptionsUsecase.Result.UserNotFound -> ListSubscriptionsResult.UserNotFound
                is ListSubscriptionsUsecase.Result.Success -> ListSubscriptionsResult.Success(
                    result.paging.map { userDetailsList -> userDetailsList.map { it.serializable() } }.serializable()
                )
            }
        }
    }
    listSubscriptions(repository)
}
