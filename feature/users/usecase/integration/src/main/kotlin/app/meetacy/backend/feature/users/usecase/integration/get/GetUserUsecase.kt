package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import app.meetacy.backend.feature.users.usecase.get.ViewUserUsecase
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUser() {
    val getUserViews by singleton<GetUsersViewsUsecase> {
        val viewUserRepository: GetUsersViewsUsecase.ViewUserRepository by getting
        val storage = object : GetUsersViewsUsecase.Storage {
            private val usersStorage: UsersStorage by getting

            override suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?> =
                usersStorage.getUsersOrNull(userIdentities)
        }
        GetUsersViewsUsecase(storage, viewUserRepository)
    }
}

internal fun DIBuilder.viewUser() {
    val viewUser by singleton<ViewUserUsecase> {
        val filesRepository: FilesRepository by getting
        val storage = object : ViewUserUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting

            override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
                friendsStorage.isSubscribed(userId, subscriberId)

        }

        ViewUserUsecase(filesRepository, storage)
    }
}
