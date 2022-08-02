@file:UseSerializers(AccessTokenSerializer::class, MeetingIdSerializer::class)

package app.meetacy.backend.endpoint.meetings.participate


import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.serialization.AccessTokenSerializer
import app.meetacy.backend.types.serialization.MeetingIdSerializer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class ParticipateParam(
    val meetingId: MeetingId,
    val meetingAccessHash: String,
    val accessToken: AccessToken
)

sealed interface ParticipateMeetingResult {
    object Success : ParticipateMeetingResult
    object TokenInvalid : ParticipateMeetingResult
    object MeetingNotFound : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    fun participateMeeting(
        meetingId: MeetingId,
        meetingAccessHash: String,
        accessToken: AccessToken
    ) : ParticipateMeetingResult
}

@Serializable
data class ParticipateMeetResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.participateMeeting(participateMeetingRepository: ParticipateMeetingRepository) = post("/participate") {
    val params = call.receive<ParticipateParam>()

    val result = when(
        participateMeetingRepository.participateMeeting(
            params.meetingId,
            params.meetingAccessHash,
            params.accessToken
        )
    ) {
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
