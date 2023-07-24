@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.friends.location.stream

import app.meetacy.backend.database.integration.location.stream.DatabaseFriendsLocationStreamingStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.friends.location.FriendsLocationDependencies
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.friends.location.stream.UsecaseStreamLocationRepository
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase

val DI.friendLocationDependencies: FriendsLocationDependencies by Dependency

fun DIBuilder.locationStreamDependencies() {
    val friendLocationDependencies by singleton<FriendsLocationDependencies> {
        FriendsLocationDependencies(
            streamLocationRepository = UsecaseStreamLocationRepository(
                usecase = FriendsLocationStreamingUsecase(
                    authRepository = authRepository,
                    storage = DatabaseFriendsLocationStreamingStorage(database),
                    usersViewsRepository = getUserViewsRepository
                )
            )
        )
    }
}
