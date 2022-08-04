@file:UseSerializers(AccessHashSerializer::class, AccessTokenSerializer::class, UserIdSerializer::class, MeetingIdSerializer::class)

package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.serialization.AccessHashSerializer
import app.meetacy.backend.types.serialization.AccessTokenSerializer
import app.meetacy.backend.types.serialization.MeetingIdSerializer
import app.meetacy.backend.types.serialization.UserIdSerializer

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class GetParam(
    val accessToken: AccessToken,
    val meetingId: MeetingId,
    val meetingAccessHash: AccessHash
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object TokenInvalid : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface MeetingRepository {
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

fun Route.getMeeting(meetingRepository: MeetingRepository) = post("/get") {
    val params = call.receive<GetParam>()

    val result = when(
        val result = meetingRepository.getMeeting(
            params.accessToken,
            params.meetingId,
            params.meetingAccessHash
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
