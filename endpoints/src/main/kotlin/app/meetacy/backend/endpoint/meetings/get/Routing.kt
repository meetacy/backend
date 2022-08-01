package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.types.Meeting
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetParam(
    val accessToken: String,
    val meetingId: Long,
    val meetingAccessHash: String
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object TokenInvalid : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface MeetingRepository {
    fun getMeeting(getParam: GetParam) : GetMeetingResult
}

@Serializable
data class GetMeetingResponse(
    val status: Boolean,
    val result: Meeting?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getMeeting(meetingRepository: MeetingRepository) = post("/get") {
    val params = call.receive<GetParam>()

    val result = when(val result = meetingRepository.getMeeting(params)) {
        is GetMeetingResult.Success -> GetMeetingResponse(
            status = true,
            result = result.meeting,
            errorCode = null,
            errorMessage = null
        )
        is  GetMeetingResult.TokenInvalid -> GetMeetingResponse(
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
