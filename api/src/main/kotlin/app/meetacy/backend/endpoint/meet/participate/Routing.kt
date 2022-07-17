package app.meetacy.backend.endpoint.meet.participate


import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ParticipateParam(
    val meetingId: Long,
    val meetingAccessHash: String,
    val accessToken: String
)

sealed interface ParticipateMeetingResult {
    object Success : ParticipateMeetingResult
    object TokenInvalid : ParticipateMeetingResult
    object IdInvalid : ParticipateMeetingResult
    object HashInvalid : ParticipateMeetingResult
}

interface ParticipateMeeting {
    fun participateMeet(participateParam: ParticipateParam) : ParticipateMeetingResult
}

@Serializable
data class ParticipateMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.participateMeet(participateMeeting: ParticipateMeeting) = post("/participate") {
    val params = call.receive<ParticipateParam>()
    val result = participateMeeting.participateMeet(params)

    when(result) {
        is ParticipateMeetingResult.Success -> call.respond(
            ParticipateMeetResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        is ParticipateMeetingResult.TokenInvalid -> call.respond(
            ParticipateMeetResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid token" /* There is also an option
                  to make just one generic "Participation failed" response to
                  all three errors as a protection against brute force. */
            )
        )
        is ParticipateMeetingResult.IdInvalid -> call.respond(
            ParticipateMeetResponse(
                status = false,
                errorCode = 3,
                errorMessage = "Please provide a valid id"
            )
        )
        is ParticipateMeetingResult.HashInvalid -> call.respond(
            ParticipateMeetResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid hash"
            )
        )
    }
}
