package app.meetacy.backend.usecase.integration.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.endpoint.types.user.UserLocationSnapshot
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsecaseStreamLocationRepository(
    private val usecase: FriendsLocationStreamingUsecase
) : StreamLocationRepository {

    override suspend fun flow(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>
    ): StreamLocationRepository.Result {
        val result = usecase.flow(
            accessIdentity,
            selfLocation
        )

        return when (result) {
            is Result.Ready -> StreamLocationRepository.Result.Ready(
                flow = result.flow.map { user -> user.mapToEndpoint() }
            )
            is Result.TokenInvalid -> StreamLocationRepository.Result.TokenInvalid
        }
    }
}
