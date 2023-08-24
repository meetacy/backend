package app.meetacy.backend.feature.meetings.endpoints.integration.map.list

import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapRepository
import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapResult
import app.meetacy.backend.feature.meetings.endpoints.map.list.listMeetingsMap
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase.Result
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.listMeetingsMap() {
    val listMeetingsMapUsecase: ListMeetingsMapUsecase by di.getting

    val repository = ListMeetingsMapRepository { token, location ->
        when (
            val result = listMeetingsMapUsecase.getMeetingsList(
                accessIdentity = token.type(),
                location = location.type()
            )
        ) {
            Result.InvalidAccessIdentity -> ListMeetingsMapResult.InvalidIdentity
            is Result.Success -> ListMeetingsMapResult.Success(
                meetings = result.meetings.map { it.serializable() }
            )
        }
    }

    listMeetingsMap(repository)
}
