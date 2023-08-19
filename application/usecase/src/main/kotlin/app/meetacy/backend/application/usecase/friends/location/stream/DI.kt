package app.meetacy.backend.application.usecase.friends.location.stream

import app.meetacy.backend.feature.friends.endpoints.location.FriendsLocationDependencies
import app.meetacy.backend.infrastructure.database.friends.location.stream.friendLocationStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.friends.usecase.integration.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.friendLocationDependencies: FriendsLocationDependencies by Dependency

fun DIBuilder.locationStreamDependencies() {
    val friendLocationDependencies by singleton<FriendsLocationDependencies> {
        FriendsLocationDependencies(
            streamLocationRepository = UsecaseStreamLocationRepository(
                usecase = FriendsLocationStreamingUsecase(
                    authRepository = get(),
                    storage = friendLocationStorage,
                    usersViewsRepository = getUserViewsRepository
                )
            )
        )
    }
}
