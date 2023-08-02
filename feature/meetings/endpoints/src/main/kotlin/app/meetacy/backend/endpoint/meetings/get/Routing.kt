package app.meetacy.backend.endpoint.meetings.get

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meeting.MeetingIdentity
import app.meetacy.backend.types.serializable.meeting.type
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class GetMeetingsParam(
    val token: AccessIdentitySerializable,
    val meetingId: app.meetacy.backend.types.serializable.meeting.MeetingIdentity
)

sealed interface GetMeetingResult {
    class Success(val meeting: Meeting) : GetMeetingResult
    object InvalidAccessIdentity : GetMeetingResult
    object MeetingNotFound : GetMeetingResult
}

interface GetMeetingRepository {
    suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): GetMeetingResult
}

fun Route.getMeetings(getMeetingRepository: GetMeetingRepository) = post("/get") {
    val params = call.receive<GetMeetingsParam>()

    when (
        val result = getMeetingRepository.getMeeting(
            params.token.type(),
            params.meetingId
        )
    ) {
        is GetMeetingResult.Success -> call.respondSuccess(
            result.meeting
        )

        is GetMeetingResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)

        is GetMeetingResult.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
    }
}
