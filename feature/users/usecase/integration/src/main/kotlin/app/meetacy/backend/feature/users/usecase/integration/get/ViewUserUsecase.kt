package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.users.usecase.get.ViewUserUsecase
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewUserUsecase() {
    val viewUserUsecase by singleton {
        val filesRepository: FilesRepository by getting

        val friendsStorage: FriendsStorage by getting

        val storage = object : ViewUserUsecase.Storage {
            override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
                friendsStorage.isSubscribed(userId, subscriberId)
        }

        ViewUserUsecase(filesRepository, storage)
    }
}
