package app.meetacy.backend.endpoint.meetings.participate


import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import app.meetacy.backend.types.serialization.MeetingIdSerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ParticipateParam(
    val meetingIdentity: MeetingIdentitySerializable,
    val accessToken: AccessTokenSerializable
)

sealed interface ParticipateMeetingResult {
    object Success : ParticipateMeetingResult
    object TokenInvalid : ParticipateMeetingResult
    object MeetingNotFound : ParticipateMeetingResult
}

interface ParticipateMeetingRepository {
    suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
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
            params.meetingIdentity.type(),
            params.accessToken.type()
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
