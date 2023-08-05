package app.meetacy.backend.infrastructure.integrations.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.friends.location.stream.friendLocationStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.friendLocationDependencies: FriendsLocationDependencies by Dependency

fun DIBuilder.locationStreamDependencies() {
    val friendLocationDependencies by singleton<FriendsLocationDependencies> {
        FriendsLocationDependencies(
            streamLocationRepository = UsecaseStreamLocationRepository(
                usecase = FriendsLocationStreamingUsecase(
                    authRepository = authRepository,
                    storage = friendLocationStorage,
                    usersViewsRepository = getUserViewsRepository
                )
            )
        )
    }
}
