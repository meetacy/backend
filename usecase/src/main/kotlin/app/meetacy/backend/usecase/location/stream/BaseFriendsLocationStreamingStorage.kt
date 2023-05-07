package app.meetacy.backend.usecase.location.stream

import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.flow.Flow

class BaseFriendsLocationStreamingStorage(
    flowStorageUnderlying: LocationFlowStorage.Underlying,
    private val friendsStorage: Storage,
) : FriendsLocationStreamingUsecase.Storage {
    private val storage = LocationFlowStorage(flowStorageUnderlying)

    override suspend fun setLocation(userId: UserId, location: Location) {
        storage.setLocation(userId, location)
    }

    override suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId> =
        friendsStorage.getFriends(userId, maxAmount)

    override fun locationFlow(userId: UserId): Flow<TimedLocation> =
        storage.locationFlow(userId)

    interface Storage {
        suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId>
    }
}
