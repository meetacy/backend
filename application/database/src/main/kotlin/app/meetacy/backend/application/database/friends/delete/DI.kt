package app.meetacy.backend.application.database.friends.delete

import app.meetacy.backend.feature.friends.database.integration.friends.delete.DatabaseDeleteFriendStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.friends.usecase.delete.DeleteFriendUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteFriendStorage: DeleteFriendUsecase.Storage by Dependency

fun DIBuilder.deleteFriend() {
    val deleteFriendStorage by singleton<DeleteFriendUsecase.Storage> {
        DatabaseDeleteFriendStorage(database)
    }
}
