package app.meetacy.backend.endpoint.meetings.map.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable
import app.meetacy.backend.types.serializable.location.Location as LocationSerializable

fun interface ListMeetingsMapRepository {
    suspend fun list(
        token: AccessIdentity,
        location: LocationSerializable
    ): ListMeetingsResult
}

sealed interface ListMeetingsResult {
    class Success(val meetings: List<Meeting>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

@Serializable
private data class ListMeetingsMapParams(
    val token: AccessIdentitySerializable,
    val location: LocationSerializable
)

fun Route.listMeetingsMap() = post("/list") {
    val listMeetingsMapRepository: ListMeetingsMapRepository by di.getting

    val params = call.receive<ListMeetingsMapParams>()

    when (
        val result = listMeetingsMapRepository.list(params.token.type(), params.location)
    ) {
        is ListMeetingsResult.InvalidIdentity ->
            call.respondFailure(Failure.InvalidToken)
        is ListMeetingsResult.Success ->
            call.respondSuccess(result.meetings)
    }
}
