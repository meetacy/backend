package app.meetacy.backend.usecase.friends.location.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId
import app.meetacy.backend.usecase.types.getUserView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FriendsLocationStreamingUsecase(
    private val auth: AuthRepository,
    private val storage: Storage,
    private val usersViewsRepository: GetUsersViewsRepository,
    private val maxFriends: Amount = 5_000.amount
) {

    suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        collector: FlowCollector<UserOnMap>
    ): Result {
        val userId = auth.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        coroutineScope {
            selfLocation.onEach { location ->
                storage.setLocation(userId, location)
            }.launchIn(scope = this)

            val friendIds = storage.getFriends(maxFriends)

            for (friendId in friendIds) launch {
                storage
                    .locationsFlow(friendId)
                    .collect { location ->
                        val updatedFriend = usersViewsRepository.getUserView(userId, friendId)
                        collector.emit(
                            value = UserOnMap(
                                user = updatedFriend,
                                updatedLocation = location
                            )
                        )
                    }
            }
        }

        return Result.LocationStreamed
    }

    sealed interface Result {
        object TokenInvalid : Result
        object LocationStreamed : Result
    }

    interface Storage {
        suspend fun setLocation(userId: UserId, location: Location)
        suspend fun getFriends(maxAmount: Amount): List<UserId>
        fun locationsFlow(userId: UserId): Flow<UpdatedLocation>
    }
}
