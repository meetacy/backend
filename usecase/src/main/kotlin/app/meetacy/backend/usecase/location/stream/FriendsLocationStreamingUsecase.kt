package app.meetacy.backend.usecase.location.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FriendsLocationStreamingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val usersViewsRepository: GetUsersViewsRepository,
    private val maxFriends: Amount = 5_000.amount
) {

    suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        channel: Channel<UserOnMap>
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        coroutineScope {
            selfLocation.onEach { location ->
                storage.setLocation(userId, location)
            }.launchIn(scope = this)

            val friendIds = storage.getFriends(userId, maxFriends)

            for (friendId in friendIds) launch {
                storage
                    .locationFlow(friendId)
                    .collect { location ->
                        val updatedFriend = usersViewsRepository.getUserView(userId, friendId)
                        channel.send(
                            element = UserOnMap(
                                user = updatedFriend,
                                location = location
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
        suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId>
        fun locationFlow(userId: UserId): Flow<TimedLocation>
    }

}
