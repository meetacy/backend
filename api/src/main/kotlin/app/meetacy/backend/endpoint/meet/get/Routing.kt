package app.meetacy.backend.endpoint.meet.get

import app.meetacy.backend.endpoint.models.Meeting
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
    object IdInvalid : GetMeetingResult
    object HashInvalid : GetMeetingResult
}

interface GetMeeting {
    fun getMeet(getParam: GetParam) : GetMeetingResult
}

@Serializable
data class GetMeetingResponse(
    val status: Boolean,
    val result: Meeting?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getMeet(getMeeting: GetMeeting) = post("/get") {
    val params = call.receive<GetParam>()
    val result = getMeeting.getMeet(params)

    when(result) {
        is GetMeetingResult.Success -> call.respond(
            GetMeetingResponse(
                status = true,
                result = result.meeting,
                errorCode = null,
                errorMessage = null
            )
        )
        is  GetMeetingResult.TokenInvalid -> call.respond(
            GetMeetingResponse(
                status = false,
                result = null,
                errorCode = 1,
                errorMessage = "Please provide a valid token" /* There is also an option
                  to make just one generic "Meeting not found" response to
                  all three errors as a protection against brute force. */
            )
        )
        is GetMeetingResult.IdInvalid -> call.respond(
            GetMeetingResponse(
                status = false,
                result = null,
                errorCode = 2,
                errorMessage = "Please provide a valid id"
            )
        )
        is GetMeetingResult.HashInvalid -> call.respond(
            GetMeetingResponse(
                status = false,
                result = null,
                errorCode = 3,
                errorMessage = "Please provide a valid hash"
            )
        )
    }
}
