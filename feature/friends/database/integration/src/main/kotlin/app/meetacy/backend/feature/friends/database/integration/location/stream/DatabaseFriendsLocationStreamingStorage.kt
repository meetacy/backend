package app.meetacy.backend.feature.friends.database.integration.location.stream

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Database

class DatabaseFriendsLocationStreamingStorage(
    db: Database
) : FriendsLocationStreamingUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)
    private val locationsMiddleware = DatabaseLocationsMiddleware(db)

    override fun locationFlow(userId: UserId): Flow<LocationSnapshot> {
        return locationsMiddleware.locationFlow(userId)
    }

    override suspend fun setLocation(userId: UserId, location: Location) {
        locationsMiddleware.setLocation(userId, location)
    }

    override suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId> {
        return friendsStorage.getFriends(userId, maxAmount, pagingId = null).data
    }
}
