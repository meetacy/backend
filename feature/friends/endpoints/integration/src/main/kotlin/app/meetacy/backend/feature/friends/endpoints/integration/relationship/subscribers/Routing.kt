package app.meetacy.backend.feature.friends.endpoints.integration.relationship.subscribers

import app.meetacy.backend.feature.friends.endpoints.relationship.subscribers.GetSubscribersRepository
import app.meetacy.backend.feature.friends.endpoints.relationship.subscribers.GetSubscribersResult
import app.meetacy.backend.feature.friends.endpoints.relationship.subscribers.getSubscribers
import app.meetacy.backend.feature.friends.usecase.relationship.subscribers.GetSubscribersUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.subscribers(di: DI) {
    val usecase: GetSubscribersUsecase by di.getting
    val repository = object : GetSubscribersRepository {
        override suspend fun getSubscribers(identifier: GetSubscribersRepository.Identifier): GetSubscribersResult {
            return when(
                val result = usecase.getSubscribers(
                    when (identifier) {
                        is GetSubscribersRepository.Identifier.ByUserId -> GetSubscribersUsecase.Identifier.ByUserId(
                            identity = identifier.userId.type(),
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                        is GetSubscribersRepository.Identifier.Self -> GetSubscribersUsecase.Identifier.Self(
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                    }
                )
            ) {
                GetSubscribersUsecase.Result.InvalidToken -> GetSubscribersResult.InvalidIdentity
                GetSubscribersUsecase.Result.UserNotFound -> GetSubscribersResult.UserNotFound
                is GetSubscribersUsecase.Result.Success -> GetSubscribersResult.Success(
                    result.paging.map { userDetailsList -> userDetailsList.map { it.serializable() } }.serializable()
                )
            }
        }
    }
    getSubscribers(repository)
}
