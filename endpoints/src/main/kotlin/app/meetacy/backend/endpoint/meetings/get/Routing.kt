package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.endpoint.types.ServerFailure
import app.meetacy.backend.endpoint.types.ServerResponse
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetMeetingsParam(
    val accessIdentity: AccessIdentitySerializable,
    val meetingIdentity: MeetingIdentitySerializable
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object TokenInvalid : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface GetMeetingRepository {
    suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ) : GetMeetingResult
}

@Serializable
data class GetMeetingResponse(
    val status: Boolean,
    val result: Meeting?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getMeetings(getMeetingRepository: GetMeetingRepository) = post("/get") {
    val params = call.receive<GetMeetingsParam>()

    when(
        val result = getMeetingRepository.getMeeting(
            params.accessIdentity.type(),
            params.meetingIdentity.type()
        )
    ) {
        is GetMeetingResult.Success -> call.respond(
            ServerResponse(result.meeting)
        )
        is GetMeetingResult.TokenInvalid -> call.respond(
            ServerFailure(1, "Please provide a valid token")
        )
        is GetMeetingResult.MeetingNotFound -> call.respond(
            ServerFailure(2, "Please provide a valid meetingIdentity")
        )
    }
}
