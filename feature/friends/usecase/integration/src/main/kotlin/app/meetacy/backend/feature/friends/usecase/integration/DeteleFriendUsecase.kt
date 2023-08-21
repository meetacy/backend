package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.delete.DeleteFriendUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.deleteFriendUsecase() {
    val deleteFriendUsecase by singleton<DeleteFriendUsecase> {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : DeleteFriendUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting

            override suspend fun deleteFriend(userId: UserId, friendId: UserId) {
                friendsStorage.deleteFriend(userId, friendId)
            }

            override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
                friendsStorage.isSubscribed(userId, friendId)
        }

        DeleteFriendUsecase(authRepository, getUsersViewsRepository, storage)
    }
}
