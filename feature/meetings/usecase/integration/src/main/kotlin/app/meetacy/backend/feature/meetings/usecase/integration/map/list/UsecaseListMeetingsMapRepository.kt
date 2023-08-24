package app.meetacy.backend.feature.meetings.usecase.integration.map.list

import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapRepository
import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable
import app.meetacy.backend.types.serializable.location.Location as LocationSerializable

class UsecaseListMeetingsMapRepository(
    private val usecase: ListMeetingsMapUsecase
) : ListMeetingsMapRepository {
    override suspend fun list(
        token: AccessIdentitySerializable,
        location: LocationSerializable
    ): ListMeetingsMapResult =
        when (val result = usecase.getMeetingsList(token.type(), location.type())) {
            is Result.InvalidAccessIdentity -> ListMeetingsMapResult.InvalidIdentity
            is Result.Success -> ListMeetingsMapResult.Success(
                meetings = result.meetings.map { it.serializable() }
            )
        }
}
