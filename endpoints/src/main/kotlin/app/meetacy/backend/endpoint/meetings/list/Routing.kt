package app.meetacy.backend.endpoint.meetings.list

import app.meetacy.backend.endpoint.ktor.ResponseCode
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListParam(
    val accessIdentity: AccessIdentitySerializable
)

sealed interface ListMeetingsResult {
    class Success(val meetings: List<Meeting>) : ListMeetingsResult
    object InvalidIdentity : ListMeetingsResult
}

interface MeetingsListRepository {
    suspend fun getList(accessIdentity: AccessIdentity): ListMeetingsResult
}

fun Route.listMeetings(meetingsListRepository: MeetingsListRepository) = post("/list") {
    val params = call.receive<ListParam>()
    when (val result = meetingsListRepository.getList(params.accessIdentity.type())) {
        is ListMeetingsResult.Success -> call.respondSuccess(result.meetings)
        is ListMeetingsResult.InvalidIdentity -> call.respondFailure(ResponseCode.InvalidAccessIdentity)
    }
}
