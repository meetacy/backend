package app.meetacy.backend.feature.friends.endpoints.integration.subscribers.list

import app.meetacy.backend.feature.friends.endpoints.subscribers.list.ListSubscribersRepository
import app.meetacy.backend.feature.friends.endpoints.subscribers.list.ListSubscribersResult
import app.meetacy.backend.feature.friends.endpoints.subscribers.list.listSubscribers
import app.meetacy.backend.feature.friends.usecase.subscribers.list.ListSubscribersUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.listSubscribers(di: DI) {
    val usecase: ListSubscribersUsecase by di.getting
    val repository = object : ListSubscribersRepository {
        override suspend fun listSubscribers(identifier: ListSubscribersRepository.Identifier): ListSubscribersResult {
            return when(
                val result = usecase.listSubscribers(
                    when (identifier) {
                        is ListSubscribersRepository.Identifier.ByUserId -> ListSubscribersUsecase.Identifier.ByUserId(
                            identity = identifier.userId.type(),
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                        is ListSubscribersRepository.Identifier.Self -> ListSubscribersUsecase.Identifier.Self(
                            accessIdentity = identifier.accessIdentity.type(),
                            amount = identifier.amount.type(),
                            pagingId = identifier.pagingId?.type()
                        )
                    }
                )
            ) {
                ListSubscribersUsecase.Result.InvalidToken -> ListSubscribersResult.InvalidIdentity
                ListSubscribersUsecase.Result.UserNotFound -> ListSubscribersResult.UserNotFound
                is ListSubscribersUsecase.Result.Success -> ListSubscribersResult.Success(
                    result.paging.map { userDetailsList -> userDetailsList.map { it.serializable() } }.serializable()
                )
            }
        }
    }
    listSubscribers(repository)
}
