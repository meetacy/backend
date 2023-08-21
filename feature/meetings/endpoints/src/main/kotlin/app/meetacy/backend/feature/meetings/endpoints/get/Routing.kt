package app.meetacy.backend.feature.meetings.endpoints.get

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class GetMeetingsParam(
    val token: AccessIdentitySerializable,
    val meetingId: MeetingIdentity
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object InvalidAccessIdentity : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface GetMeetingRepository {
    suspend fun getMeeting(
        accessIdentity: AccessIdentitySerializable,
        meetingIdentity: MeetingIdentity
    ): GetMeetingResult
}

fun Route.getMeetings(provider: GetMeetingRepository) = post("/get") {
    val params = call.receive<GetMeetingsParam>()

    when (
        val result = provider.getMeeting(
            params.token,
            params.meetingId
        )
    ) {
        is GetMeetingResult.Success -> call.respondSuccess(result.meeting)
        is GetMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        is GetMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
