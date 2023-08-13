package app.meetacy.backend.feature.meetings.endpoints.map.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun interface ListMeetingsMapRepository {
    suspend fun list(
        token: AccessIdentity,
        location: Location
    ): ListMeetingsResult
}

sealed interface ListMeetingsResult {
    class Success(val meetings: List<Meeting>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

@Serializable
private data class ListMeetingsMapParams(
    val token: AccessIdentity,
    val location: Location
)

fun Route.listMeetingsMap() = post("/list") {
    val listMeetingsMapRepository: ListMeetingsMapRepository by di.getting

    val params = call.receive<ListMeetingsMapParams>()

    when (
        val result = listMeetingsMapRepository.list(params.token, params.location)
    ) {
        is ListMeetingsResult.InvalidIdentity ->
            call.respondFailure(Failure.InvalidToken)
        is ListMeetingsResult.Success ->
            call.respondSuccess(result.meetings)
    }
}
