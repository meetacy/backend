package app.meetacy.backend.infrastructure.database.friends.add

import app.meetacy.backend.feature.friends.database.integration.friends.add.DatabaseAddFriendStorage
import app.meetacy.backend.feature.notifications.database.integration.notifications.DatabaseAddNotificationUsecaseStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.updates.updatesMiddleware
import app.meetacy.backend.feature.friends.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.feature.notifications.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.addFriendStorage: AddFriendUsecase.Storage by Dependency

fun DIBuilder.addFriend() {
    val addFriendStorage by singleton<AddFriendUsecase.Storage> {
        DatabaseAddFriendStorage(
            database,
            AddNotificationUsecase(
                DatabaseAddNotificationUsecaseStorage(
                    database, updatesMiddleware
                )
            )
        )
    }
}
