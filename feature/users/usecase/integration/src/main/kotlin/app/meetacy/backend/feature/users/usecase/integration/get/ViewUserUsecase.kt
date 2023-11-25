package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.users.usecase.get.ViewUsersUsecase
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewUserUsecase() {
    val viewUsersUsecase by singleton {
        val filesRepository: FilesRepository by getting

        val friendsStorage: FriendsStorage by getting

        val storage = object : ViewUsersUsecase.Storage {
            override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
                friendsStorage.isSubscribed(userId, subscriberId)
        }

        ViewUsersUsecase(filesRepository, storage)
    }
}
