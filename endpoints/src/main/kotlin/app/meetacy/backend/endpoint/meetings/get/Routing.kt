package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.types.Meeting
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
    val accessToken: AccessIdentitySerializable,
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

    val result = when(
        val result = getMeetingRepository.getMeeting(
            params.accessToken.type(),
            params.meetingIdentity.type()
        )
    ) {
        is GetMeetingResult.Success -> GetMeetingResponse(
            status = true,
            result = result.meeting,
            errorCode = null,
            errorMessage = null
        )
        is GetMeetingResult.TokenInvalid -> GetMeetingResponse(
            status = false,
            result = null,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        is GetMeetingResult.MeetingNotFound -> GetMeetingResponse(
            status = false,
            result = null,
            errorCode = 2,
            errorMessage = "Please provide a valid id"
        )
    }

    call.respond(result)
}
