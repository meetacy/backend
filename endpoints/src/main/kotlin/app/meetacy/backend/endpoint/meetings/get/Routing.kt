package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import app.meetacy.backend.types.serialization.MeetingIdSerializable
import app.meetacy.backend.types.serialization.UserIdSerializable

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class GetParam(
    val accessToken: AccessTokenSerializable,
    val meetingId: MeetingIdSerializable,
    val meetingAccessHash: AccessHashSerializable
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object TokenInvalid : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface GetMeetingRepository {
    suspend fun getMeeting(
        accessToken: AccessToken,
        meetingId: MeetingId,
        meetingAccessHash: AccessHash
    ) : GetMeetingResult
}

@Serializable
data class GetMeetingResponse(
    val status: Boolean,
    val result: Meeting?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getMeeting(getMeetingRepository: GetMeetingRepository) = post("/get") {
    val params = call.receive<GetParam>()

    val result = when(
        val result = getMeetingRepository.getMeeting(
            params.accessToken.type(),
            params.meetingId.type(),
            params.meetingAccessHash.type()
        )
    ) {
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
