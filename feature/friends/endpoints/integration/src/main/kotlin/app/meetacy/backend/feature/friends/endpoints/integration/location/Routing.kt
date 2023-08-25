package app.meetacy.backend.feature.friends.endpoints.integration.location

import app.meetacy.backend.feature.friends.endpoints.location.FriendsLocationDependencies
import app.meetacy.backend.feature.friends.endpoints.location.friendsLocation
import app.meetacy.backend.feature.friends.endpoints.location.stream.StreamLocationRepository
import app.meetacy.backend.feature.friends.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.users.mapToEndpoint
import app.meetacy.di.global.di
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Route.location() {
    val usecase: FriendsLocationStreamingUsecase by di.getting
    val repository = object : StreamLocationRepository {
        override suspend fun flow(
            accessIdentity: AccessIdentity,
            selfLocation: Flow<Location>
        ): StreamLocationRepository.Result {
            val result = usecase.flow(
                accessIdentity.type(),
                selfLocation.map(Location::type)
            )
            return when (result) {
                is FriendsLocationStreamingUsecase.Result.Ready -> StreamLocationRepository.Result.Ready(
                    flow = result.flow.map { user -> user.mapToEndpoint() }
                )
                is FriendsLocationStreamingUsecase.Result.TokenInvalid -> StreamLocationRepository.Result.TokenInvalid
            }
        }
    }
    val deps = FriendsLocationDependencies(repository)

    friendsLocation(deps)
}
