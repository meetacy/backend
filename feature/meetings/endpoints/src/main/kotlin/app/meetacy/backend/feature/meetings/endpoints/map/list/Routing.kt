package app.meetacy.backend.feature.meetings.endpoints.map.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.meetings.Meeting
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun interface ListMeetingsMapRepository {
    suspend fun list(
        token: AccessIdentity,
        location: Location
    ): ListMeetingsMapResult
}

sealed interface ListMeetingsMapResult {
    data class Success(val meetings: List<Meeting>) : ListMeetingsMapResult
    data object InvalidIdentity : ListMeetingsMapResult
}

@Serializable
private data class ListMeetingsMapParams(
    val token: AccessIdentity,
    val location: Location
)

fun Route.listMeetingsMap(repository: ListMeetingsMapRepository) = post("/list") {
    val params = call.receive<ListMeetingsMapParams>()

    when (
        val result = repository.list(params.token, params.location)
    ) {
        is ListMeetingsMapResult.InvalidIdentity ->
            call.respondFailure(Failure.InvalidToken)
        is ListMeetingsMapResult.Success ->
            call.respondSuccess(result.meetings)
    }
}
