package app.meetacy.backend.feature.friends.endpoints.integration.list

import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsBody
import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsRepository
import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsResult
import app.meetacy.backend.feature.friends.endpoints.list.listFriends
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.mapToEndpoint
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.listFriends() {
    val usecase: ListFriendsUsecase by di.getting
    val repository = object : ListFriendsRepository {
        override suspend fun getFriends(token: ListFriendsBody): ListFriendsResult =
            when (
                val result = usecase.getFriendsUsecase(
                    accessIdentity = token.token.type(),
                    amount = token.amount.type(),
                    pagingId = token.pagingId?.type()
                )
            ) {
                ListFriendsUsecase.Result.InvalidToken ->
                    ListFriendsResult.InvalidIdentity
                is ListFriendsUsecase.Result.Success ->
                    ListFriendsResult.Success(
                        result.paging.map { users ->
                            users.map { it.mapToEndpoint() }
                        }.serializable()
                    )
            }
    }
    listFriends(repository)
}
