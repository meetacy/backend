package app.meetacy.backend.endpoint.meetings.participate


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
    object MeetingNotFound : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    fun participateMeet(participateParam: ParticipateParam) : ParticipateMeetingResult
}

@Serializable
data class ParticipateMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.participateMeet(participateMeetingRepository: ParticipateMeetingRepository) = post("/participate") {
    val params = call.receive<ParticipateParam>()

    val result = when(participateMeetingRepository.participateMeet(params)) {
        is ParticipateMeetingResult.Success -> ParticipateMeetResponse(
            status = true,
            errorCode = null,
            errorMessage = null
        )
        is ParticipateMeetingResult.TokenInvalid -> ParticipateMeetResponse(
            status = false,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        is ParticipateMeetingResult.MeetingNotFound -> ParticipateMeetResponse(
            status = false,
            errorCode = 3,
            errorMessage = "Please provide a valid id"
        )
    }

    call.respond(result)
}
