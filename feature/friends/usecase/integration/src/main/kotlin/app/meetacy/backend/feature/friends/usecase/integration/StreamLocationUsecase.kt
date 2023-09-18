package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.database.location.UsersLocationsStorage
import app.meetacy.backend.feature.friends.usecase.location.LocationsMiddleware
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Database

fun DIBuilder.locationMiddleware() {
    val locationMiddleware by singleton<LocationsMiddleware> {
        val storage = object : LocationsMiddleware.Storage {
            private val database: Database by getting
            private val table = UsersLocationsStorage(database)

            override suspend fun setLocation(userId: UserId, location: Location) {
                table.saveLocation(userId, location)
            }

            override suspend fun getLocation(userId: UserId): LocationSnapshot? {
                return table.getLocation(userId)
            }
        }
        LocationsMiddleware(storage)
    }
}

fun DIBuilder.locationStreamingUsecase() {
    val locationStreamingUsecase by singleton<FriendsLocationStreamingUsecase> {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : FriendsLocationStreamingUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting
            private val locationsMiddleware: LocationsMiddleware by getting

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
        FriendsLocationStreamingUsecase(authRepository, storage, getUsersViewsRepository)
    }
}
