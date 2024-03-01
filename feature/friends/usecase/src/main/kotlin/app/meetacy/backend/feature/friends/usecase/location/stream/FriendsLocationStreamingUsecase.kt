package app.meetacy.backend.feature.friends.usecase.location.stream

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.amount.amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserLocationSnapshot
import app.meetacy.backend.types.users.getUsersViews
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.milliseconds

class FriendsLocationStreamingUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val usersViewsRepository: GetUsersViewsRepository,
    private val maxFriends: Amount = 5_000.amount
) {

    @OptIn(FlowPreview::class)
    suspend fun flow(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        val flow = channelFlow {
            val shared = selfLocation.onEach { location ->
                storage.setLocation(userId, location)
            }.shareIn(this, SharingStarted.Eagerly, 1)

            shared.firstOrNull() ?: return@channelFlow

            val friends = storage.getFriends(userId, maxFriends)
            val friendViews = usersViewsRepository.getUsersViews(userId, friends).iterator()

            friends.onEach { friendId ->
                storage
                    .locationFlow(friendId)
                    .sample(300.milliseconds)
                    .map { location ->
                        UserLocationSnapshot(
                            user = friendViews.next(),
                            location = location
                        )
                    }
                    .collect { userLocationSnapshot ->
                        channel.send(userLocationSnapshot)
                    }
            }
        }

        return Result.Ready(flow)
    }

    sealed interface Result {
        data object TokenInvalid : Result
        class Ready(val flow: Flow<UserLocationSnapshot>) : Result
    }

    interface Storage {
        suspend fun setLocation(userId: UserId, location: Location)
        suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId>
        fun locationFlow(userId: UserId): Flow<LocationSnapshot>
    }
}
