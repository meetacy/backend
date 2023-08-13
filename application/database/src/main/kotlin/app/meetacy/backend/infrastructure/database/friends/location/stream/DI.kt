package app.meetacy.backend.infrastructure.database.friends.location.stream

import app.meetacy.backend.feature.friends.database.integration.location.stream.DatabaseFriendsLocationStreamingStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.friendLocationStorage: FriendsLocationStreamingUsecase.Storage by Dependency

fun DIBuilder.locationStream() {
    val friendLocationStorage by singleton<FriendsLocationStreamingUsecase.Storage> {
        DatabaseFriendsLocationStreamingStorage(database)
    }
}
