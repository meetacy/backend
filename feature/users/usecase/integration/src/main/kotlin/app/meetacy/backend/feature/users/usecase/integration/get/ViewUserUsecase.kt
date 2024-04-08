package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.users.usecase.get.ViewUsersUsecase
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewUserUsecase() {
    val viewUsersUsecase by singleton {
        val filesRepository: FilesRepository by getting

        val friendsStorage: FriendsStorage by getting

        val storage = object : ViewUsersUsecase.Storage {
            override suspend fun isSubscribers(subscriptionData: List<ViewUsersUsecase.SubscriptionData>): List<Boolean> =
                friendsStorage.isSubscribers(
                    subscriptionData.map { data ->
                        FriendsStorage.IsSubscriber(data.userId, data.subscriberId)
                    }
                )
        }

        ViewUsersUsecase(filesRepository, storage)
    }
}
