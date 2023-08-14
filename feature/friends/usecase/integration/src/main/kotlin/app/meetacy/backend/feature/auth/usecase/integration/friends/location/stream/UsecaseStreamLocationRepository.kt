package app.meetacy.backend.feature.auth.usecase.integration.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.location.stream.FriendsLocationStreamingUsecase
import app.meetacy.backend.feature.auth.usecase.location.stream.FriendsLocationStreamingUsecase.Result
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
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
