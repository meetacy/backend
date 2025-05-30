package app.meetacy.backend.feature.friends.usecase.integration.location.stream

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
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
