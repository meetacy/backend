package app.meetacy.backend.endpoint.meetings.map.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun interface ListMeetingsMapRepository {
    suspend fun list(accessIdentity: AccessIdentity): ListMeetingsResult
}

sealed interface ListMeetingsResult {
    class Success(val meetings: List<Meeting>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

@Serializable
private data class ListMeetingsMapParams(val token: AccessIdentitySerializable)

fun Route.listMeetingsMap(
    listMeetingsMapRepository: ListMeetingsMapRepository
) = post("/list") {
    val params = call.receive<ListMeetingsMapParams>()

    when (
        val result = listMeetingsMapRepository.list(params.token.type())
    ) {
        is ListMeetingsResult.InvalidIdentity ->
            call.respondFailure(Failure.InvalidAccessIdentity)
        is ListMeetingsResult.Success ->
            call.respondSuccess(result.meetings)
    }
}
